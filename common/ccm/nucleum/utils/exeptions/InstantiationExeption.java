package ccm.nucleum.utils.exeptions;

import ccm.nucleum.IMod;

/**
 * This Exception is not MY Fault, You tried to make a instance of a class that
 * was not meant to be instantiated
 */
public class InstantiationExeption extends NotMyFaultExeption {
    
    private static final long   serialVersionUID = -8965983875808595830L;
    
    private final StringBuilder tmpErrorSB       = new StringBuilder();
    
    public InstantiationExeption(final IMod mod) {
        super(mod);
        addString();
        crashMC();
    }
    
    private void addString() {
        tmpErrorSB.append("Why did the Hex did you try to Instantiate my Helper???\n");
        tmpErrorSB.append("You ... Better stop tring to, you will nevar get the instance!!\n");
        errorSB.replace(errorSB.lastIndexOf("|"),
                        errorSB.lastIndexOf("|") + 1,
                        tmpErrorSB.toString());
    }
}