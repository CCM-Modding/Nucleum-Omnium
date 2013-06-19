package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

public class FileExeption extends NotMyFaultExeption {
    
    /**
     * This Exception is not MY Fault, YOU ALTERED MY folders
     */
    private static final long   serialVersionUID = -629455840055671187L;
    
    private final IMod          mod;
    
    private final String        languageLocation;
    
    private final StringBuilder tmpErrorSB       = new StringBuilder();
    
    public FileExeption(final IMod mod, final String languageLocation) {
        super(mod);
        this.mod = mod;
        this.languageLocation = languageLocation;
        addString();
        crashMC();
    }
    
    private void addString() {
        tmpErrorSB.append("Delete any file that does NOT end with '.xml' from your lang folder located inside ");
        tmpErrorSB.append(languageLocation);
        tmpErrorSB.append(" which is inside of the ");
        tmpErrorSB.append(mod.getName());
        tmpErrorSB.append(".jar in your mods folder\n");
        errorSB.replace(errorSB.lastIndexOf("|"), errorSB.lastIndexOf("|") + 1, tmpErrorSB.toString());
    }
}