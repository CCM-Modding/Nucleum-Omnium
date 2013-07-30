package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

/**
 * Logger Not Found Exception
 * <p>
 * Commonly thrown when a Mod fails to register its logger and tries to log something
 * 
 * @author Captain_Shadows
 */
public class LNFExeption extends NotMyFaultExeption {

    private static final long   serialVersionUID = 1080172170005877086L;

    private final StringBuilder tmpErrorSB       = new StringBuilder();

    public LNFExeption(final IMod mod) {
        super(mod);
        addString();
    }

    private void addString() {
        tmpErrorSB.append(mod.getName());
        tmpErrorSB.append(" Forgot to register it's Logger before using it! \n");
        tmpErrorSB.append("Please go tell the creator to fix it!! \n");
        errorSB.replace(errorSB.lastIndexOf("|"), errorSB.lastIndexOf("|") + 1, tmpErrorSB.toString());
    }
}