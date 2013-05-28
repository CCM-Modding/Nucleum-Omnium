package ccm.nucleum_omnium.handler.enums;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnumHandler
{
    private Field field;
    private Object type;
    
    public EnumHandler(Class<?> container, String fieldName){
        try{
            field = container.getDeclaredField(fieldName);
            field.get(field);
            type = field;
        }catch(NoSuchFieldException e){
            e.printStackTrace();
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
    }
    
    public ItemStack getItemIS(Enum<? extends IEnum> enumType)
    {
        return new ItemStack((Item)type, 1, enumType.ordinal());
    }

    public ItemStack getItemIS(Enum<? extends IEnum> enumType, int amount)
    {
        return new ItemStack((Item)type, amount, enumType.ordinal());
    }
    
    public ItemStack getBlockIS(Enum<? extends IEnum> enumType)
    {
        return new ItemStack((Block)type, 1, enumType.ordinal());
    }

    public ItemStack getBlockIS(Enum<? extends IEnum> enumType, int amount)
    {
        return new ItemStack((Block)type, amount, enumType.ordinal());
    }
}