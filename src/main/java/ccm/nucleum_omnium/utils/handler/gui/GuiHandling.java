/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * GuiHandling
 * <p>
 * 
 * @author Captain_Shadows
 */
public class GuiHandling
{
    private final Class<?>   clazz;
    private final Class<?>[] classes;

    public GuiHandling(final Class<?> clazz, final Class<?>... classes)
    {
        this.clazz = clazz;
        this.classes = classes;
    }

    public Object createNew(final Object... objects)
    {
        boolean canContinue = true;
        for (int i = 0; i < objects.length; i++)
        {
            if ((classes[i].isInstance(objects[i])))
            {
                canContinue = false;
                break;
            }
        }
        if (canContinue)
        {
            Object instance = null;
            Constructor<?> c = null;
            try
            {
                c = clazz.getConstructor(classes);
            } catch (final NoSuchMethodException e)
            {
                e.printStackTrace();
            } catch (final SecurityException e)
            {
                e.printStackTrace();
            }

            try
            {
                instance = c.newInstance(objects);
            } catch (final InstantiationException e)
            {
                e.printStackTrace();
            } catch (final IllegalAccessException e)
            {
                e.printStackTrace();
            } catch (final IllegalArgumentException e)
            {
                e.printStackTrace();
            } catch (final InvocationTargetException e)
            {
                e.printStackTrace();
            }
            return instance;
        }
        else
        {
            return null;
        }
    }

    /**
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Object getServerGuiElement(final EntityPlayer player,
                                      final World world,
                                      final int x,
                                      final int y,
                                      final int z)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param player
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Object getClientGuiElement(final EntityPlayer player,
                                      final World world,
                                      final int x,
                                      final int y,
                                      final int z)
    {
        // TODO Auto-generated method stub
        return null;
    }
}