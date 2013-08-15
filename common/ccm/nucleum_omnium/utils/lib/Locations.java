/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.lib;

import static ccm.nucleum_omnium.utils.lib.Archive.MOD_ID;

import ccm.nucleum_omnium.base.BaseNIC;

public final class Locations extends BaseNIC
{
    public static final String PROXY = "ccm." + MOD_ID + ".proxy.";

    public static final String CLIENT_PROXY = PROXY + "ClientProxy";

    public static final String SERVER_PROXY = PROXY + "CommonProxy";

    public static final String HD_CAPES = "https://raw.github.com/CCM-Modding/Nucleum-Omnium/master/hd_capes.txt";

    public static final String CAPES = "https://raw.github.com/CCM-Modding/Nucleum-Omnium/master/capes.txt";

    public static final String GUI = "textures/guis/gui%s.png";

    public static final String MODEL = "models/%s.obj";

    public static final String MODEL_TEXTURE = "textures/models/model%s.png";
}