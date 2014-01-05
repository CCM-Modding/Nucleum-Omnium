/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.api.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ccm.nucleum.omnium.utils.helper.item.WrappedStack;

/**
 * REDO Recipe
 * <p>
 * Recipe Class
 * 
 * @author Captain_Shadows
 */
public class Recipe
{
    /** All the inputs */
    private final List<WrappedStack> inputs;
    /** All the outputs */
    private final List<WrappedStack> outputs;
    /** True if the call to inputs.size() returns > 1 */
    private final boolean hasMultipleInputs;
    /** True if the call to outputs.size() returns > 1 */
    private final boolean hasMultipleOutputs;

    public Recipe(final List<?> inputs, final List<?> outputs)
    {
        if ((inputs != null) && (outputs != null))
        {
            if ((inputs.size() >= 1) && (outputs.size() >= 1))
            {
                this.inputs = null;// WrappedStack.toWrappedList(inputs);
                this.outputs = null;// WrappedStack.toWrappedList(outputs);
            } else
            {
                throw new RuntimeException("The lists where less than 1 in size");
            }
        } else
        {
            throw new RuntimeException("The lists where null");
        }
        hasMultipleInputs = inputs.size() > 1 ? true : false;
        hasMultipleOutputs = outputs.size() > 1 ? true : false;
    }

    public Recipe(final Object input, final Object output)
    {
        this(Arrays.asList(new WrappedStack(input)), Arrays.asList(new WrappedStack(output)));
    }

    public List<WrappedStack> getInputs()
    {
        return new ArrayList<WrappedStack>(inputs);
    }

    public List<WrappedStack> getOutputs()
    {
        return new ArrayList<WrappedStack>(outputs);
    }

    public boolean hasMultipleInputs()
    {
        return hasMultipleInputs;
    }

    public boolean hasMultipleOutputs()
    {
        return hasMultipleOutputs;
    }

    public WrappedStack getInput()
    {
        return inputs.get(0);
    }

    public WrappedStack getOutput()
    {
        return outputs.get(0);
    }

    public boolean isInput(Object item)
    {
        return inputs.contains(new WrappedStack(item));
    }

    public boolean isOutput(Object item)
    {
        return outputs.contains(new WrappedStack(item));
    }
}