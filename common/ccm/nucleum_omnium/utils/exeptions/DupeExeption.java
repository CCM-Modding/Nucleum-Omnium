package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

public class DupeExeption extends NotMyFaultExeption
{

    /**
     * This Exception is not MY Fault
     */
    private static final long   serialVersionUID = -6762134744912730876L;

    private final IMod          mod;

    private final StringBuilder tmpErrorSB       = new StringBuilder();

    public DupeExeption(final IMod mod)
    {
        super(mod);
        this.mod = mod;
        this.addString();
        this.crashMC();
    }

    private void addString()
    {
        this.tmpErrorSB.append("Why did you install my Mod twice?\n Remove the second ");
        this.tmpErrorSB.append(this.mod.getName());
        this.tmpErrorSB.append("-Universal-");
        this.tmpErrorSB.append(this.mod.getVersion());
        this.tmpErrorSB.append(".jar out of your mods-Folder.\n ");
        this.tmpErrorSB.append("You only need one of them.\n ");
        this.tmpErrorSB.append("And another Question: Why the Hax did Forge not detect that before me?\n");
        this.errorSB.replace(this.errorSB.lastIndexOf("|"), this.errorSB.lastIndexOf("|") + 1, this.tmpErrorSB.toString());
    }

    @Override
    public String toString()
    {
        return this.errorSB.toString();
    }
}