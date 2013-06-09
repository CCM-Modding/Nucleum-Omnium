package ccm.nucleum_omnium.utils.lib;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import net.minecraftforge.common.Property;

public class Properties
{

    public static boolean                   mystLoaded    = false;

    public static final String              CLIENT_SIDE   = "Client Only";

    public static final String              SERVER_SIDE   = "Server Only";

    public static boolean                   rain          = false;

    public static List<Integer>             freeBlockIDs  = new ArrayList<Integer>();

    public static List<Integer>             freeItemIDs   = new ArrayList<Integer>();

    public static TreeMap<String, Property> knownBlockIds = new TreeMap<String, Property>();

    public static TreeMap<String, Property> knownItemIds  = new TreeMap<String, Property>();
}