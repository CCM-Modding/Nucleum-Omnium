package ccm.nucleum.omnium.utils.handler.compatibility;

/**
 * Runnable Interface implemented by all classes that contain code that does <b>NOT</b> interact with other mods.
 * 
 * @author Captain_Shadows
 */
public interface NonInteractCompatibility
{
    /** @return the ID of the mod that you need to be loaded */
    public String id();

    /** What to do if the Mod is found to be loaded */
    public void init();
}