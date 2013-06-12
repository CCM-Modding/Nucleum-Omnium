package ccm.nucleum.helper;

import java.io.File;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

/**
 * Use this class to save per-server data.
 * 
 * @author Dries007
 *
 */
public class DataHelper
{
    private static File root;
    
    /**
     * To be called on server start.
     */
    public static void init()
    {
        root = new File(DimensionManager.getCurrentSaveRootDirectory(), "CCM-Modding");
        root.mkdirs();
    }
    
    /**
     * Use to get a (new) folder for your mod only.
     * @param modID
     * @return File instance of the folder. It exists.
     */
    public static File getModFolder(final String modID)
    {
        File folder = new File(root, modID);
        folder.mkdirs();
        return folder;
    }
    
    /**
     * Make sure you don't just override the old file. Read it, then manipulate, then save.
     * 
     * @param modID for the folder.
     * @param fileName, don't add an extension.
     * @param data in NBT format.
     * @return true if success.
     */
    public static boolean saveData(final String modID, String fileName, NBTTagCompound data)
    {
        try
        {
            File folder = getModFolder(modID);
            
            File tempFile = new File(folder, fileName.trim() + "_tmp.dat");
            File realFile = new File(folder, fileName.trim() + ".dat");
            
            CompressedStreamTools.write(data, tempFile);
            
            if (realFile.exists()) realFile.delete();
            
            tempFile.renameTo(realFile);
            
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Use this before you save data...
     * 
     * @param modID for the folder
     * @param fileName, don't add an extension.
     * @return data stored or new NBTTagCompound if file didn't exist.
     */
    public static NBTTagCompound readData(final String modID, String fileName)
    {
        try
        {
            File folder = getModFolder(modID);
            
            File file = new File(folder, fileName.trim() + ".dat");
            
            if (!file.exists()) return new NBTTagCompound();
            else return CompressedStreamTools.read(file);
                
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new NBTTagCompound();
        }
    }
    
    /**
     * Remove a data file.
     * 
     * @param modID for the folder
     * @param fileName, don't add an extension.
     * @return true if 
     */
    public static boolean deleteFile(final String modID, String fileName)
    {
        try
        {
            File folder = getModFolder(modID);
            File file = new File(folder, fileName.trim() + ".dat");
            
            return file.delete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
