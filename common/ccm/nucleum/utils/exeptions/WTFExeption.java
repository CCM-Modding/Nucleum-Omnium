package ccm.nucleum.utils.exeptions;

import ccm.nucleum.IMod;

/**
 * The User tried to use the TPX command with coordinates
 */
public class WTFExeption extends NotMyFaultExeption {
    
    private static final long   serialVersionUID = 6333535650002729746L;
    
    private final StringBuilder tmpErrorSB       = new StringBuilder();
    
    public WTFExeption(final IMod mod) {
        super(mod);
        addString();
    }
    
    private void addString() {
        tmpErrorSB.append("What the Hax are you tring to do???\n");
        tmpErrorSB.append("DO NOT TRY TO GO TO THE FAR LANDS!!\n");
        tmpErrorSB.append("Herobrine Lives there... \n");
        errorSB.replace(errorSB.lastIndexOf("|"),
                        errorSB.lastIndexOf("|") + 1,
                        tmpErrorSB.toString());
    }
}