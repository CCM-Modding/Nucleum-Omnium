/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.utils.handler.gui;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * GuiHandling
 * <p>
 * 
 * @author Captain_Shadows
 */
public class GuiHandling<T>
{
    private final Class<T>   clazz;
    private final Class<?>[] classes;

    public GuiHandling(final Class<T> clazz, final Class<?>... classes)
    {
        this.clazz = clazz;
        this.classes = classes;
    }

    public T createNew(final Object... objects)
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
            T instance = null;
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
                instance = (T) c.newInstance(objects);
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
}