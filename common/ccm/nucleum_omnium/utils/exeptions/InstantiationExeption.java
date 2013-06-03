package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

public class InstantiationExeption extends NotMyFaultExeption
{

    /**
     * This Exception is not MY Fault, You tried to make a instance of a Helper class
     */
    private static final long   serialVersionUID = -8965983875808595830L;

    private final StringBuilder tmpErrorSB       = new StringBuilder();

    public InstantiationExeption(final IMod mod)
    {
        super(mod);
        this.addString();
        this.crashMC();
    }

    private void addString()
    {
        this.tmpErrorSB.append("Why did the Hex did you try to Instantiate my Helper???\n");
        this.tmpErrorSB.append("You ... Better stop tring to, you will nevar get the instance!!\n");
        this.errorSB.replace(this.errorSB.lastIndexOf("|"), this.errorSB.lastIndexOf("|") + 1, this.tmpErrorSB.toString());
    }

    @Override
    public String toString()
    {
        return this.errorSB.toString();
    }
}