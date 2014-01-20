/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * NBTHelper
 */
public final class NBTHelper
{
    /**
     * Initializes the NBT Tag Compound for the given item if it is null
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
    public static int getInteger(final ItemStack item, final String keyName)
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

    public static NBTTagCompound getTag(final ItemStack item, final String keyName)
    {
        initCompound(item);
        if (!item.getTagCompound().hasKey(keyName))
        {
            setTag(item, keyName, new NBTTagCompound());
        }
        return item.getTagCompound().getCompoundTag(keyName);
    }

    public static void setTag(final ItemStack item, final String keyName, final NBTTagCompound keyValue)
    {
        initCompound(item);
        item.getTagCompound().setTag(keyName, keyValue);
    }

    // NON ITEM BASED NBT
    // String
    public static String getString(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setString(keyName, "");
        }
        return nbt.getString(keyName);
    }

    // boolean
    public static boolean getBoolean(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setBoolean(keyName, false);
        }
        return nbt.getBoolean(keyName);
    }

    // byte
    public static byte getByte(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setByte(keyName, (byte) 0);
        }
        return nbt.getByte(keyName);
    }

    // short
    public static short getShort(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setShort(keyName, (short) 0);
        }
        return nbt.getShort(keyName);
    }

    // int
    public static int getInteger(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setInteger(keyName, 0);
        }
        return nbt.getInteger(keyName);
    }

    // long
    public static long getLong(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setLong(keyName, 0);
        }
        return nbt.getLong(keyName);
    }

    // float
    public static float getFloat(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setFloat(keyName, 0);
        }
        return nbt.getFloat(keyName);
    }

    // double
    public static double getDouble(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setDouble(keyName, 0);
        }
        return nbt.getDouble(keyName);
    }

    // Tag
    public static NBTTagCompound getTag(final NBTTagCompound nbt, final String keyName)
    {
        if (!nbt.hasKey(keyName))
        {
            nbt.setCompoundTag(keyName, new NBTTagCompound());
        }
        return nbt.getCompoundTag(keyName);
    }
}