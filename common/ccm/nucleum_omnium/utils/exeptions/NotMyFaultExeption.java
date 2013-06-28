package ccm.nucleum_omnium.utils.exeptions;

import net.minecraft.client.Minecraft;
import net.minecraft.crash.CrashReport;
import ccm.nucleum_omnium.IMod;

/**
 * This Exception, and All its sub-Classes are NOT my Fault
 */
public class NotMyFaultExeption extends RuntimeException {

	private static final long	serialVersionUID	= -4392997358096412890L;

	private final IMod			mod;

	protected StringBuilder		errorSB				= new StringBuilder();

	public NotMyFaultExeption(final IMod mod) {
		this.mod = mod;
		addString();
	}

	private void addString() {
		errorSB.append("The mod '");
		errorSB.append(mod.getName());
		errorSB.append("' has a Problem.\nIT'S NOT MY FAULT! ");
		errorSB.append("Below is how to fix it.\n \n");
		errorSB.append("|");
		errorSB.append("\nDO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!\n");
	}

	protected void crashMC() {
		Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(this, toString()));
	}

	@Override
	public String toString() {
		return errorSB.toString();
	}
}