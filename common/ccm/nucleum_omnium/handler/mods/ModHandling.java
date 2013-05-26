package ccm.nucleum_omnium.handler.mods;

public class ModHandling
{

    private final IModHandler handler;

    private final String      modName;

    private final String      modExeption;

    public ModHandling(final IModHandler handler,
                       final String modName,
                       final String modExeption)
    {
        this.handler = handler;
        this.modName = modName;
        this.modExeption = modExeption;
    }

    public IModHandler getHandler()
    {
        return this.handler;
    }

    public String getModName()
    {
        return this.modName;
    }

    public String getModExeption()
    {
        return this.modExeption;
    }
}