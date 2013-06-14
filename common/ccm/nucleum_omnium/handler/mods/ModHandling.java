package ccm.nucleum_omnium.handler.mods;

public final class ModHandling {
    
    private final IModHandler handler;
    
    private final String      modName;
    
    private final String      modExeption;
    
    public ModHandling(final IModHandler handler, final String modExeption) {
        this.handler = handler;
        modName = handler.getModName();
        this.modExeption = modExeption;
    }
    
    public IModHandler getHandler() {
        return handler;
    }
    
    public String getModName() {
        return modName;
    }
    
    public String getModExeption() {
        return modExeption;
    }
}