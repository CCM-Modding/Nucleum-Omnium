/**
 * Copyright (c) <2012>, Oleg Romanovskiy <shedarhome@gmail.com> aka Shedar All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met: * Redistributions of source code
 * must retain the above copyright notice, this list of conditions and the following
 * disclaimer. * Redistributions in binary form must reproduce the above copyright notice, this list
 * of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. * Neither the name of the author nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY
 * OF SUCH DAMAGE.
 */

package lib.org.modstats.reporter.v1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lib.org.modstats.ModVersionData;
import lib.org.modstats.ModsUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.crash.CallableMinecraftVersion;
import net.minecraftforge.common.MinecraftForge;
import argo.jdom.JdomParser;
import argo.jdom.JsonNode;
import argo.jdom.JsonRootNode;
import argo.jdom.JsonStringNode;
import argo.saj.InvalidSyntaxException;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.versioning.ComparableVersion;

class DataSender extends Thread {

	private static final String	urlAutoTemplate		= "http://modstats.org/api/v1/report?mc=%s&user=%s&data=%s&sign=%s&beta=%b&strict=%b";

	private static final String	urlManualTemplate	= "http://modstats.org/api/v1/check?mc=%s&user=%s&data=%s&sign=%s&beta=%b&strict=%b";

	private final Reporter		reporter;

	public final boolean		manual;

	public DataSender(final Reporter reporter, final boolean manual) {
		this.reporter = reporter;
		this.manual = manual;
	}

	private String toHexString(final byte[] bytes) {
		final char[] hexArray = { '0',
				'1',
				'2',
				'3',
				'4',
				'5',
				'6',
				'7',
				'8',
				'9',
				'a',
				'b',
				'c',
				'd',
				'e',
				'f' };
		final char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v / 16];
			hexChars[(j * 2) + 1] = hexArray[v % 16];
		}
		return new String(hexChars);
	}

	private String getPlayerId() throws IOException {
		final File statDir = new File(Minecraft.getMinecraftDir(), "stats");
		if (!statDir.exists()) {
			statDir.mkdirs();
		}
		String mac = "";
		try {
			final InetAddress address = InetAddress.getLocalHost();
			final NetworkInterface ni = NetworkInterface.getByInetAddress(address);
			final byte[] macArray = ni.getHardwareAddress();
			if (macArray != null) {
				mac = toHexString(macArray);
			}
		} catch (final Exception ex) {}
		final File uidFile = new File(statDir, "player.uid");
		if (uidFile.exists() && uidFile.canRead() && (uidFile.length() == (32 + mac.length()))) {
			final String data = Files.toString(uidFile, Charsets.US_ASCII);
			final String storedMac = data.substring(32);
			if (storedMac.equalsIgnoreCase(mac)) {
				return data.substring(0, 32);
			}
		}
		uidFile.createNewFile();
		if (uidFile.canWrite()) {
			final String uid = UUID.randomUUID().toString().replace("-", "");
			final FileOutputStream output = new FileOutputStream(uidFile);
			output.write((uid + mac).getBytes());
			output.close();
			return uid;
		}
		return "";
	}

	private String getSignature(final String data) {
		return Hashing.md5().hashString(data).toString();
	}

	private String getData() {
		final StringBuilder b = new StringBuilder();
		for (final Map.Entry<String, ModVersionData> item : reporter.registeredMods.entrySet()) {
			b.append(item.getKey()).append("+").append(item.getValue().version).append("$");
		}
		return b.toString();
	}

	private boolean checkIsNewer(final String current, final String received) {
		return new ComparableVersion(received).compareTo(new ComparableVersion(current)) > 0;
	}

	private void parseResponse(final String response) {
		try {
			final JsonRootNode json = new JdomParser().parse(response);
			// empty result
			if (!json.isNode("mods")) {
				FMLLog.info("[Modstats] Empty result");
				return;
			}
			final List<JsonNode> modList = json.getArrayNode("mods");
			final ModsUpdateEvent event = new ModsUpdateEvent();
			for (final JsonNode modObject : modList) {
				final String prefix = modObject.getStringValue("code");
				if (!reporter.registeredMods.containsKey(prefix)) {
					FMLLog.warning("[Modstats] Extra mod '%s' in service response", prefix);
					continue;
				}
				final String version = modObject.getStringValue("ver");
				if ((version == null) || version.equals(reporter.registeredMods.get(prefix).version)) {
					continue;
				}
				if (checkIsNewer(reporter.registeredMods.get(prefix).version, version)) {
					final ModVersionData data = new ModVersionData(	prefix,
																	reporter.registeredMods.get(prefix).name,
																	version);
					final Map<JsonStringNode, JsonNode> fields = modObject.getFields();
					for (final Map.Entry<JsonStringNode, JsonNode> entry : fields.entrySet()) {
						final String fieldName = entry.getKey().getText();
						if (fieldName.equals("code") || fieldName.equals("ver")) {
							continue;
						}
						if (!(entry.getValue() instanceof JsonStringNode)) {
							FMLLog.warning(String.format(	"[Modstats] Too complex data in response for field '%s'.",
															fieldName));
							continue;
						}
						final String value = ((JsonStringNode) entry.getValue()).getText();
						if (fieldName.equals("chlog")) {
							data.changeLogUrl = value;
						} else if (fieldName.equals("link")) {
							data.downloadUrl = value;
						} else {
							data.extraFields.put(fieldName, value);
						}
					}
					event.add(data);
				}

			}
			if (event.getUpdatedMods().size() > 0) {
				MinecraftForge.EVENT_BUS.post(event);
			}
			if (!event.isCanceled() && (event.getUpdatedMods().size() > 0)) {
				final List<ModVersionData> updatedModsToOutput = event.getUpdatedMods();
				final StringBuilder builder = new StringBuilder("Updates found: ");
				final Iterator<ModVersionData> iterator = updatedModsToOutput.iterator();
				while (iterator.hasNext()) {
					final ModVersionData modVersionData = iterator.next();
					builder.append(modVersionData.name)
							.append(" (")
							.append(modVersionData.version)
							.append(")")
							.append(iterator.hasNext() ? "," : ".");
				}
				FMLLog.info("[Modstats] %s", builder.toString());
				if (!reporter.config.logOnly && FMLCommonHandler.instance().getSide().isClient()) {
					final Minecraft mc = FMLClientHandler.instance().getClient();
					int maxTries = 30;
					while ((mc.thePlayer == null) && (maxTries > 0)) {
						try {
							Thread.sleep(1000);
						} catch (final InterruptedException e) {}
						maxTries--;
					}
					if (mc.thePlayer != null) {
						mc.thePlayer.addChatMessage(builder.toString());
					}
				}
			}

		} catch (final InvalidSyntaxException e) {
			FMLLog.warning("[Modstats] Can't parse response: '%s'.", e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			final String data = getData();
			final String playerId = getPlayerId();
			final String hash = getSignature(playerId + "!" + data);
			final String template = manual ? DataSender.urlManualTemplate : DataSender.urlAutoTemplate;
			final String mcVersion = new CallableMinecraftVersion(null).minecraftVersion();
			final URL url = new URL(String.format(	template,
													mcVersion,
													playerId,
													data,
													hash,
													reporter.config.betaNotifications,
													reporter.config.forCurrentMinecraftVersion));
			final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			String out = "";
			while ((line = reader.readLine()) != null) {
				// in most cases it will contain just one line
				out += line;
			}
			reader.close();
			parseResponse(out);
		} catch (final MalformedURLException e) {
			FMLLog.warning("[Modstats] Invalid stat report url");
		} catch (final IOException e) {
			FMLLog.info("[Modstats] Stat wasn't reported '" + e.getMessage() + "'");
		} catch (final Exception e) {
			FMLLog.warning("[Modstats] Something wrong: " + e.toString());
		}
	}
}
