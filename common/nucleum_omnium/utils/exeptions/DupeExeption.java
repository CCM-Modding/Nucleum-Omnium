package nucleum_omnium.utils.exeptions;

public class DupeExeption extends RuntimeException
{

    /**
     * This Exception is not MY Fault
     */
    private static final long serialVersionUID = -6762134744912730876L;

    private final String      modName;

    private final String      mError;

    public DupeExeption(final String error,
                        final String modName)
    {
        this.mError = error;
        this.modName = modName;
    }

    @Override
    public String toString()
    {
        return "The mod "
               + modName
               + " has a Problem.\nIT'S NOT MY FAULT! Below is how to fix it.\n"
               + this.mError
               + "\nDO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!";
    }
}