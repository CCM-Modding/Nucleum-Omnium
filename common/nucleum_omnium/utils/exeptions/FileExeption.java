package nucleum_omnium.utils.exeptions;

public class FileExeption extends RuntimeException
{

    /**
     * This Exception is not MY Fault, YOU ALTERED MY folders
     */
    private static final long serialVersionUID = -629455840055671187L;

    private final String      modName;

    private final String      languageLocation;

    public FileExeption(final String modName,
                        final String languageLocation)
    {
        this.modName = modName;
        this.languageLocation = languageLocation;
    }

    @Override
    public String toString()
    {
        return "The mod"
               + modName
               + " has had a Problem loading it's Language files.\nIT'S NOT MY FAULT! Below is how to fix it.\n"
               + "/nDelete any file that does NOT end with '.xml' from your lang folder located inside "
               + languageLocation
               + " which is inside of the "
               + modName
               + ".jar in your mods folder/n"
               + "\nDO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!";
    }
}