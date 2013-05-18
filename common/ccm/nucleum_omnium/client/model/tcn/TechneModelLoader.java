package ccm.nucleum_omnium.client.model.tcn;

import java.net.URL;

import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.IModelCustomLoader;
import net.minecraftforge.client.model.ModelFormatException;

public class TechneModelLoader implements IModelCustomLoader
{

    @Override
    public String getType()
    {
        return "Techne model";
    }

    private static final String[] types =
                                        { "tcn" };

    @Override
    public String[] getSuffixes()
    {
        return types;
    }

    @Override
    public IModelCustom loadInstance(final String resourceName, final URL resource) throws ModelFormatException
    {
        return new TechneModel(resourceName, resource);
    }

}