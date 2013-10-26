/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.exeptions;

import ccm.nucleum.omnium.CCMMod;


/**
 * This Exception, and All its sub-Classes are NOT my Fault
 */
public class NotMyFaultExeption extends MainException
{

    private static final long serialVersionUID = -501876681172804351L;

    protected final CCMMod mod;

    protected final StringBuilder errorSB = new StringBuilder();

    public NotMyFaultExeption(final CCMMod mod)
    {
        super("THIS EXEPTION WAS NOT MY FAULT!");
        this.mod = mod;
        addString();
    }

    private void addString()
    {
        errorSB.append("The mod '");
        errorSB.append(mod.getName());
        errorSB.append("' has a Problem.\nIT'S NOT MY FAULT! ");
        errorSB.append("Below is how to fix it.\n \n");
        errorSB.append("|");
        errorSB.append("\nDO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!\n");
    }

    @Override
    public String toString()
    {
        return errorSB.toString();
    }
}