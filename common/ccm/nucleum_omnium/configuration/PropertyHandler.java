package ccm.nucleum_omnium.configuration;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import ccm.nucleum_omnium.IMod;

public class PropertyHandler
{

    private final int                             baseItemID;

    private final int                             baseBlockID;

    private static HashMap<IMod, Boolean>         hasPropertyHandler = new HashMap<IMod, Boolean>();

    private static HashMap<IMod, PropertyHandler> idHandler          = new HashMap<IMod, PropertyHandler>();

    private PropertyHandler(final int baseItemID,
                            final int baseBlockID)
    {
        this.baseItemID = baseItemID;
        this.baseBlockID = baseBlockID;
    }

    public static PropertyHandler getConfigInstance(final IMod requester, final int baseItemID, final int baseBlockID)
    {
        if (!hasPropertyHandler.containsKey(requester)){
            hasPropertyHandler.put(requester, true);
            idHandler.put(requester, new PropertyHandler(baseItemID, baseBlockID));
        }
        return idHandler.get(requester);
    }

    /**
     * @return The next Item ID
     */
    public Property getNextItem(final Configuration config, final String itemDescription)
    {
        for (int i = 0; i < Item.itemsList.length; i++){
            if (Item.itemsList[i] == null){
                return config.getItem(itemDescription, i);
            }
        }
        return config.getItem(itemDescription, this.baseItemID);
    }

    /**
     * @return The next Block ID
     */
    public Property getNextBlock(final Configuration config, final String blockDescription)
    {
        for (int i = 0; i < Block.blocksList.length; i++){
            if (Block.blocksList[i] == null){
                return config.getBlock(blockDescription, i);
            }
        }
        return config.getBlock(blockDescription, this.baseBlockID);
    }
}