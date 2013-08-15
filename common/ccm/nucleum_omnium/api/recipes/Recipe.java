/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum_omnium.api.recipes;

import net.minecraft.item.ItemStack;

/**
 * Recipe
 * <p>
 * Simple Recipe class
 * 
 * @author Captain_Shadows
 */
public class Recipe
{

    private final ItemStack input;

    private final ItemStack output;

    private ItemStack output2;

    private final boolean hasSecondOutput;

    public Recipe(final ItemStack input, final ItemStack output)
    {
        this.input = input;
        this.output = output;
        hasSecondOutput = false;
    }

    public Recipe(final ItemStack input, final ItemStack output, final ItemStack output2)
    {
        this.input = input;
        this.output = output;
        this.output2 = output2;
        hasSecondOutput = true;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Recipe))
        {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if (hasSecondOutput != other.hasSecondOutput)
        {
            return false;
        }
        if (input == null)
        {
            if (other.input != null)
            {
                return false;
            }
        } else if (!input.equals(other.input))
        {
            return false;
        }
        if (output == null)
        {
            if (other.output != null)
            {
                return false;
            }
        } else if (!output.equals(other.output))
        {
            return false;
        }
        if (output2 == null)
        {
            if (other.output2 != null)
            {
                return false;
            }
        } else if (!output2.equals(other.output2))
        {
            return false;
        }
        return true;
    }

    public ItemStack getOutput()
    {
        return output.copy();
    }

    public ItemStack getOutput2()
    {
        return output2.copy();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (hasSecondOutput ? 1231 : 1237);
        result = (prime * result) + ((input == null) ? 0 : input.hashCode());
        result = (prime * result) + ((output == null) ? 0 : output.hashCode());
        result = (prime * result) + ((output2 == null) ? 0 : output2.hashCode());
        return result;
    }

    public boolean hasSecondOutput()
    {
        return hasSecondOutput;
    }

    public boolean isInput(final ItemStack stack)
    {
        return input.isItemEqual(stack);
    }

    public boolean isOutput(final ItemStack stack)
    {
        return ((output.isItemEqual(stack)) || (output2.isItemEqual(stack)));
    }

    @Override
    public String toString()
    {
        final StringBuilder builder = new StringBuilder();
        builder.append("Recipe [");
        if (input != null)
        {
            builder.append("input=").append(input).append(", ");
        }
        if (output != null)
        {
            builder.append("output=").append(output).append(", ");
        }
        if (output2 != null)
        {
            builder.append("output2=").append(output2).append(", ");
        }
        builder.append("hasSecondOutput=").append(hasSecondOutput).append(", ").append(", hashCode()=").append(hashCode()).append(", ").append("]");
        return builder.toString();
    }
}