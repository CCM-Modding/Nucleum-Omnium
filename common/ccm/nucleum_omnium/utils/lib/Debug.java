/**
 * 
 */
package ccm.nucleum_omnium.utils.lib;

import java.util.logging.Level;

/**
 * @author Captain_Shadows
 */
public final class Debug extends Level {

    private static final long serialVersionUID = 3658980284693455425L;

    public Debug(final String name, final int value, final String resourceBundleName) {
        super(name, value, resourceBundleName);
    }
}