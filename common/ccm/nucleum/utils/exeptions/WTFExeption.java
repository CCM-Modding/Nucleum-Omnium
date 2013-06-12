package ccm.nucleum.utils.exeptions;

import ccm.nucleum.IMod;

public class WTFExeption extends NotMyFaultExeption
{

    /**
     * 
     */
    private static final long   serialVersionUID = 6333535650002729746L;

    private final StringBuilder tmpErrorSB       = new StringBuilder();

    public WTFExeption(final IMod mod)
    {
        super(mod);
        this.addString();
        this.crashMC();
    }

    private void addString()
    {
        this.tmpErrorSB.append("What the Hax are you tring to do???\n");
        this.tmpErrorSB.append("DO NOT TRY TO GO TO THE FAR LANDS!!\n");
        this.tmpErrorSB.append("Herobrine Lives there... \n");
        this.errorSB.replace(this.errorSB.lastIndexOf("|"), this.errorSB.lastIndexOf("|") + 1, this.tmpErrorSB.toString());
    }
}