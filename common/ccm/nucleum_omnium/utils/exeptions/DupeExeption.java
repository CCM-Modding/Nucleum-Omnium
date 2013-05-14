package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

public class DupeExeption extends RuntimeException
{

    /**
     * This Exception is not MY Fault
     */
    private static final long serialVersionUID = -6762134744912730876L;

    private final IMod        mod;

    private final String      mError;

    public DupeExeption(final IMod mod,
                        final String error)
    {
        this.mod = mod;
        this.mError = error;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("The mod ");
        sb.append(mod.getModName());
        sb.append(" has a Problem.\nIT'S NOT MY FAULT! Below is how to fix it.\n");
        sb.append(this.mError);
        sb.append("\nDO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!");
        return sb.toString();
    }
}