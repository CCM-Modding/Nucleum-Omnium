/**
 * CCM Modding, Nucleum-Omnium
 */
package ccm.nucleum.omnium.utils.helper;

import net.minecraft.nbt.NBTTagCompound;

import ccm.nucleum.omnium.base.BaseNIC;

/**
 * NBTHelper
 * <p>
 * 
 * @author Captain_Shadows
 */
public class NBTHelper extends BaseNIC
{

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
    public static int getInt(final NBTTagCompound nbt, final String keyName)
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
}