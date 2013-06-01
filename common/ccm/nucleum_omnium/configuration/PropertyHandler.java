package ccm.nucleum_omnium.configuration;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class PropertyHandler
{

    private final int           baseItemID;

    private final int           baseBlockID;

    private final List<Integer> freeItemIDs  = new LinkedList<Integer>();

    private final List<Integer> freeBlockIDs = new LinkedList<Integer>();

    public PropertyHandler(final int baseItemID,
                           final int baseBlockID)
    {
        this.baseItemID = baseItemID;
        this.baseBlockID = baseBlockID;
    }

    /**
     * @return The next Item ID
     */
    public Property getNextUsableItemID(final Configuration config, final String itemName)
    {
        if (this.freeItemIDs.isEmpty()){
            this.resetItemIDs();
            if (this.freeItemIDs.get(0) == 0){
                return config.getItem(itemName, this.baseItemID);
            }else{
                return config.getItem(itemName, this.freeItemIDs.remove(0));
            }
        }else{
            return config.getItem(itemName, this.freeItemIDs.remove(0));
        }
    }

    /**
     * @return The next Block ID
     */
    public Property getNextUsableBlockID(final Configuration config, final String blockName)
    {
        if (this.freeBlockIDs.isEmpty()){
            this.resetBlockIDs();
            if (this.freeBlockIDs.get(0) == 0){
                return config.getBlock(blockName, this.baseBlockID);
            }else{
                return config.getBlock(blockName, this.freeBlockIDs.remove(0));
            }
        }else{
            return config.getBlock(blockName, this.freeBlockIDs.remove(0));
        }
    }

    private void resetItemIDs()
    {
        for (int i = 6000; i < Item.itemsList.length; i++){
            if (Item.itemsList[i] == null){
                this.freeItemIDs.add(i);
            }
        }
    }

    private void resetBlockIDs()
    {
        for (int i = 300; i < Block.blocksList.length; i++){
            if (Block.blocksList[i] == null){
                this.freeBlockIDs.add(i);
            }
        }
    }
}