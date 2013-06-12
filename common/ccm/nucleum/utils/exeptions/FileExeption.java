package ccm.nucleum.utils.exeptions;

import ccm.nucleum.IMod;

public class FileExeption extends NotMyFaultExeption
{

    /**
     * This Exception is not MY Fault, YOU ALTERED MY folders
     */
    private static final long   serialVersionUID = -629455840055671187L;

    private final IMod          mod;

    private final String        languageLocation;

    private final StringBuilder tmpErrorSB       = new StringBuilder();

    public FileExeption(final IMod mod,
                        final String languageLocation)
    {
        super(mod);
        this.mod = mod;
        this.languageLocation = languageLocation;
        this.addString();
        this.crashMC();
    }

    private void addString()
    {
        this.tmpErrorSB.append("Delete any file that does NOT end with '.xml' from your lang folder located inside ");
        this.tmpErrorSB.append(this.languageLocation);
        this.tmpErrorSB.append(" which is inside of the ");
        this.tmpErrorSB.append(this.mod.getName());
        this.tmpErrorSB.append(".jar in your mods folder\n");
        this.errorSB.replace(this.errorSB.lastIndexOf("|"), this.errorSB.lastIndexOf("|") + 1, this.tmpErrorSB.toString());
    }
}