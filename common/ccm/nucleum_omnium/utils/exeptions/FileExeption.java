package ccm.nucleum_omnium.utils.exeptions;

import ccm.nucleum_omnium.IMod;

public class FileExeption extends RuntimeException
{

    /**
     * This Exception is not MY Fault, YOU ALTERED MY folders
     */
    private static final long serialVersionUID = -629455840055671187L;

    private final IMod        mod;

    private final String      languageLocation;

    public FileExeption(final IMod mod,
                        final String languageLocation)
    {
        this.mod = mod;
        this.languageLocation = languageLocation;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("The mod ");
        sb.append(mod.getModName());
        sb.append(" has had a Problem loading it's Language files.\nIT'S NOT MY FAULT! Below is how to fix it.\n");
        sb.append("Delete any file that does NOT end with '.xml' from your lang folder located inside ");
        sb.append(this.languageLocation);
        sb.append(" which is inside of the ");
        sb.append(this.mod.getModName());
        sb.append(".jar in your mods folder\n");
        sb.append("DO NOT COME TO ME WITH THIS. YOU CAUSED IT YOURSELF, AND I TOLD YOU HOW TO FIX IT!\n");
        return sb.toString();
    }
}