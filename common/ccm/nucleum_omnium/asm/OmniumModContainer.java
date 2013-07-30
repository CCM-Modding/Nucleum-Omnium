package ccm.nucleum_omnium.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class OmniumModContainer extends DummyModContainer {

    public OmniumModContainer() {
        super(new ModMetadata());
        final ModMetadata meta = getMetadata();
        meta.modId = "OmniumCore";
        meta.name = "CCM Core";
        meta.version = "@VERSION@";
        meta.authorList = Arrays.asList("Captain_Shadows");
        meta.url = "https://github.com/CCM-Modding/Nucleum-Omnium";
        meta.updateUrl = "http://driesgames.game-server.cc:8080/view/CCM/job/Nucleum-Omnium/";
        meta.credits = "Made By Captain Shadows, ClayCorp, Morton, and The rest of the CCM Modding Team, with special help from AbrarSyed(and the rest of the FE team, including Dries007), RebelKeithy, and a bunch of other people, Also a big thanks to The COFH Team for their Library, Jadar for Developer Capes API, and last but not least Shedar for ModStats. Also to the Forge and MCP crew, who without them no Minecraft mods would be possible";
        meta.logoFile = "/mods/nucleum-omnium/textures/logo.png";
        meta.description = "Core functionality for all CCM Mods";
    }

    @Override
    public boolean registerBus(final EventBus bus, final LoadController controller) {
        return true;
    }
}