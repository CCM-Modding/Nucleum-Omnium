package ccm.nucleum_omnium.utils.lib;

public class Languages
{

    public static final String[] LANGUAGE_FILES =
                                                { getFile("en_US"), getFile("es_ES") };

    public static String getFile(final String fileName)
    {
        return Locations.LANGUAGE_FILE + fileName + ".xml";
    }
}