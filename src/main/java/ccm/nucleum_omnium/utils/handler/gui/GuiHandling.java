/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

/**
 * GuiHandling
 * <p>
 * 
 * @author Captain_Shadows
 */
public class GuiHandling
{
    private final Class<?>   clazz;
    private final Class<?>[] classes;

    public GuiHandling(final Class<?> clazz, final Class<?>... classes)
    {
        this.clazz = clazz;
        this.classes = classes;
    }
}