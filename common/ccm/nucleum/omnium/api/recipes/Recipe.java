/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;

import ccm.nucleum.omnium.utils.helper.item.WrapperStack;

/**
 * Recipe
 * <p>
 * Recipe Class
 * 
 * @author Captain_Shadows
 */
public class Recipe
{
    /** All the inputs */
    private final List<WrapperStack> inputs;
    
    /** All the outputs */
    private final List<WrapperStack> outputs;
    
    /** True if the call to inputs.size() returns > 1 */
    private final boolean hasMultipleInputs;
    
    /** True if the call to outputs.size() returns > 1 */
    private final boolean hasMultipleOutputs;

    public Recipe(final List<WrapperStack> inputs, final List<WrapperStack> outputs)
    {
        if (inputs != null && outputs != null)
        {
            if (inputs.size() >= 1 && outputs.size() >= 1)
            {
                this.inputs = inputs;
                this.outputs = outputs;
            } else
            {
                throw new RuntimeException("The lists where less than 1 in size");
            }
        } else
        {
            throw new RuntimeException("The lists where null");
        }

        hasMultipleInputs = inputs.size() > 1 ? false : true;
        hasMultipleOutputs = outputs.size() > 1 ? false : true;
    }

    public Recipe(final WrapperStack input, final WrapperStack output)
    {
        this(Arrays.asList(input), Arrays.asList(output));
    }
    
    public Recipe(final ItemStack input, final ItemStack output)
    {
        this(new WrapperStack(input), new WrapperStack(output));
    }

    public List<WrapperStack> getInputs()
    {
        return new ArrayList<WrapperStack>(inputs);
    }

    public List<WrapperStack> getOutputs()
    {
        return new ArrayList<WrapperStack>(outputs);
    }

    public boolean hasMultipleInputs()
    {
        return hasMultipleInputs;
    }

    public boolean hasMultipleOutputs()
    {
        return hasMultipleOutputs;
    }

    public WrapperStack getInput()
    {
        return inputs.get(0);
    }

    public WrapperStack getOutput()
    {
        return outputs.get(0);
    }

    public boolean isInput(WrapperStack item)
    {
        return inputs.contains(item);
    }
    
    public boolean isOutput(WrapperStack item)
    {
        return outputs.contains(item);
    }
}