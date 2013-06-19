package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

/**
 * This Exception is not MY Fault, The user put 2 of my mods in, and somehow
 * forge let it through
 */
public class DupeExeption extends NotMyFaultExeption {
    
    private static final long   serialVersionUID = -6762134744912730876L;
    
    private final IMod          mod;
    
    private final StringBuilder tmpErrorSB       = new StringBuilder();
    
    public DupeExeption(final IMod mod) {
        super(mod);
        this.mod = mod;
        addString();
        crashMC();
    }
    
    private void addString() {
        tmpErrorSB.append("Why did you install my Mod twice?\n Remove the second ");
        tmpErrorSB.append(mod.getName());
        tmpErrorSB.append(".jar out of your mods-Folder.\n ");
        tmpErrorSB.append("You only need one of them.\n ");
        tmpErrorSB
                .append("And another Question: Why the Hax did Forge not detect that before me?\n");
        errorSB.replace(errorSB.lastIndexOf("|"),
                        errorSB.lastIndexOf("|") + 1,
                        tmpErrorSB.toString());
    }
}