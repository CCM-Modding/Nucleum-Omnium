package ccm.nucleum_omnium.client.model.tcn;

import java.net.URL;

import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.client.model.ModelFormatException;


public class TechneModel implements IModelCustom
{

    private String fileName;

    public TechneModel(String fileName, URL resource) throws ModelFormatException
    {
        this.fileName = fileName;
        loadTcnModel(resource);
    }
    
    private void loadTcnModel(URL resource)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void renderAll()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void renderPart(String partName)
    {
        // TODO Auto-generated method stub

    }
    
    @Override
    public String getType()
    {
        return "tcn";
    }
}