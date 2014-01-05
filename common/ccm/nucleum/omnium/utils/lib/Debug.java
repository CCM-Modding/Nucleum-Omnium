/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.lib;

import java.util.logging.Level;

/**
 * @author Captain_Shadows
 */
public final class Debug extends Level
{
    /**
     * long, serialVersionUID
     */
    private static final long serialVersionUID = 8515490874715990030L;
    /**
     * DEBUG indicates a Debugging tracing message. This level is initialized to <CODE>850</CODE>.
     */
    public final static Debug DEBUG = new Debug();

    public Debug()
    {
        super("DEBUG", 850, "sun.util.logging.resources.logging");
    }
}