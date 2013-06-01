package ccm.nucleum_omnium.handler.enums;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnumHandler
{

    private Field  field;

    private Object type;

    public EnumHandler(final Class<?> container,
                       final String fieldName)
    {
        try{
            this.field = container.getDeclaredField(fieldName);
            this.field.get(this.field);
            this.type = this.field;
        }catch(final NoSuchFieldException e){
            e.printStackTrace();
        }catch(final IllegalArgumentException e){
            e.printStackTrace();
        }catch(final IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public ItemStack getItemIS(final Enum<? extends IEnum> enumType)
    {
        return new ItemStack((Item) this.type, 1, enumType.ordinal());
    }

    public ItemStack getItemIS(final Enum<? extends IEnum> enumType, final int amount)
    {
        return new ItemStack((Item) this.type, amount, enumType.ordinal());
    }

    public ItemStack getBlockIS(final Enum<? extends IEnum> enumType)
    {
        return new ItemStack((Block) this.type, 1, enumType.ordinal());
    }

    public ItemStack getBlockIS(final Enum<? extends IEnum> enumType, final int amount)
    {
        return new ItemStack((Block) this.type, amount, enumType.ordinal());
    }
}