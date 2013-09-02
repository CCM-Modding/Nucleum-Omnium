/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import java.io.File;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;

import ccm.nucleum.omnium.IMod;
import ccm.nucleum.omnium.base.BaseNIC;

/**
 * Use this class to save per-server data.
 * 
 * @author Dries007
 */
public final class DataHelper extends BaseNIC
{
    private static File root;

    /**
     * To be called on server start
     */
    public static void init()
    {
        DataHelper.root = new File(DimensionManager.getCurrentSaveRootDirectory(), "CCM-Modding");
        DataHelper.root.mkdirs();
    }

    /**
     * Use to get a (new) folder for your mod only
     * 
     * @param mod
     *            The mod in "control" of the file
     * @return File instance of the folder. It exists
     */
    public static File getModFolder(final IMod mod)
    {
        final File folder = new File(DataHelper.root, mod.getModId());
        folder.mkdirs();
        return folder;
    }

    /**
     * Use this before you save data...
     * 
     * @param mod
     *            The mod in "control" of the file
     * @param fileName
     *            DO NOT add an extension
     * @return data stored or new NBTTagCompound if file didn't exist
     */
    public static NBTTagCompound readData(final IMod mod, final String fileName)
    {
        try
        {
            final File folder = DataHelper.getModFolder(mod);

            final File file = new File(folder, fileName.trim() + ".dat");

            if (!file.exists())
            {
                return new NBTTagCompound();
            } else
            {
                return CompressedStreamTools.read(file);
            }

        } catch (final Exception e)
        {
            e.printStackTrace();
            return new NBTTagCompound();
        }
    }

    /**
     * Make sure you don't just override the old file. Read it, then manipulate, then save.
     * 
     * @param mod
     *            The mod in "control" of the file
     * @param fileName
     *            DO NOT add an extension
     * @param data
     *            in NBT format
     * @return <code>true</code> if it succedes
     */
    public static boolean saveData(final IMod mod, final String fileName, final NBTTagCompound data)
    {
        try
        {
            final File folder = DataHelper.getModFolder(mod);

            final File tempFile = new File(folder, fileName.trim() + "_tmp.dat");
            final File realFile = new File(folder, fileName.trim() + ".dat");

            CompressedStreamTools.write(data, tempFile);

            if (realFile.exists())
            {
                realFile.delete();
            }

            tempFile.renameTo(realFile);

            return true;
        } catch (final Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remove a data file.
     * 
     * @param mod
     *            The mod in "control" of the file
     * @param fileName
     *            DO NOT add an extension
     * @return <code>true</code> if and only if the file or directory is successfully deleted; <code>false</code> otherwise
     */
    public static boolean deleteFile(final IMod mod, final String fileName)
    {
        try
        {
            final File folder = DataHelper.getModFolder(mod);
            final File file = new File(folder, fileName.trim() + ".dat");

            return file.delete();
        } catch (final Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}