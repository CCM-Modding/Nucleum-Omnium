/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import ccm.nucleum_omnium.base.BaseNIC;

/**
 * NBTHelper
 */
public final class NBTHelper extends BaseNIC
{

    /**
     * Initializes the NBT Tag Compound for the given item if it is null
     * 
     * @param item
     *            The item for which its NBT Tag Compound is being checked for initialization
     */
    public static void initCompound(final ItemStack item)
    {

        if (item.getTagCompound() == null)
        {
            item.setTagCompound(new NBTTagCompound());
        }
    }

    public static boolean hasTag(final ItemStack item, final String keyName)
    {

        if (item.getTagCompound() != null)
        {
            return item.getTagCompound().hasKey(keyName);
        }

        return false;
    }

    public static void removeTag(final ItemStack item, final String keyName)
    {

        if (item.getTagCompound() != null)
        {
            item.getTagCompound().removeTag(keyName);
        }
    }

    // String
    public static String getString(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setString(item, keyName, "");
        }

        return item.getTagCompound().getString(keyName);
    }

    public static void setString(final ItemStack item, final String keyName, final String keyValue)
    {

        initCompound(item);

        item.getTagCompound().setString(keyName, keyValue);
    }

    // boolean
    public static boolean getBoolean(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setBoolean(item, keyName, false);
        }

        return item.getTagCompound().getBoolean(keyName);
    }

    public static void setBoolean(final ItemStack item, final String keyName, final boolean keyValue)
    {

        initCompound(item);

        item.getTagCompound().setBoolean(keyName, keyValue);
    }

    // byte
    public static byte getByte(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setByte(item, keyName, (byte) 0);
        }

        return item.getTagCompound().getByte(keyName);
    }

    public static void setByte(final ItemStack item, final String keyName, final byte keyValue)
    {

        initCompound(item);

        item.getTagCompound().setByte(keyName, keyValue);
    }

    // short
    public static short getShort(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setShort(item, keyName, (short) 0);
        }

        return item.getTagCompound().getShort(keyName);
    }

    public static void setShort(final ItemStack item, final String keyName, final short keyValue)
    {

        initCompound(item);

        item.getTagCompound().setShort(keyName, keyValue);
    }

    // int
    public static int getInt(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setInteger(item, keyName, 0);
        }

        return item.getTagCompound().getInteger(keyName);
    }

    public static void setInteger(final ItemStack item, final String keyName, final int keyValue)
    {

        initCompound(item);

        item.getTagCompound().setInteger(keyName, keyValue);
    }

    // long
    public static long getLong(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setLong(item, keyName, 0);
        }

        return item.getTagCompound().getLong(keyName);
    }

    public static void setLong(final ItemStack item, final String keyName, final long keyValue)
    {

        initCompound(item);

        item.getTagCompound().setLong(keyName, keyValue);
    }

    // float
    public static float getFloat(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setFloat(item, keyName, 0);
        }

        return item.getTagCompound().getFloat(keyName);
    }

    public static void setFloat(final ItemStack item, final String keyName, final float keyValue)
    {

        initCompound(item);

        item.getTagCompound().setFloat(keyName, keyValue);
    }

    // double
    public static double getDouble(final ItemStack item, final String keyName)
    {

        initCompound(item);

        if (!item.getTagCompound().hasKey(keyName))
        {
            setDouble(item, keyName, 0);
        }

        return item.getTagCompound().getDouble(keyName);
    }

    public static void setDouble(final ItemStack item, final String keyName, final double keyValue)
    {

        initCompound(item);

        item.getTagCompound().setDouble(keyName, keyValue);
    }

}