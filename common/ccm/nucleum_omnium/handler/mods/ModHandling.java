package ccm.nucleum_omnium.handler.mods;

public class ModHandling
{

    private IModHandler handler;

    private String      modName;

    private String      modExeption;

    public ModHandling(IModHandler handler,
                       String modName,
                       String modExeption)
    {
        this.handler = handler;
        this.modName = modName;
        this.modExeption = modExeption;
    }

    public IModHandler getHandler()
    {
        return handler;
    }

    public String getModName()
    {
        return modName;
    }

    public String getModExeption()
    {
        return modExeption;
    }
}