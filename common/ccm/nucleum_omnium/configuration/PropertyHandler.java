package ccm.nucleum_omnium.configuration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import ccm.nucleum_omnium.utils.lib.Properties;

/**
 * This is a copy of cofh.util.ConfigHandler, edited to fit the needs of all the CCM Mods
 * 
 * @author King Lemming, Edited by: Captain Shadows
 */
public class PropertyHandler
{

    private int           baseBlockID = 300;

    private int           baseItemID  = 5000;

    private Configuration config;

    public PropertyHandler(final int baseBlockID,
                           final int baseItemID)
    {
        this.baseBlockID = baseBlockID;
        this.baseItemID = baseItemID;
    }

    public void setConfiguration(final Configuration config)
    {
        this.config = config;
        config.load();
    }

    public void updateBlockIDs(final boolean clear)
    {
        if (clear){
            Properties.freeBlockIDs.clear();
        }
        for (int i = this.baseBlockID; i < Block.blocksList.length; i++){
            if (Block.blocksList[i] == null){
                if (!Properties.freeBlockIDs.contains(Integer.valueOf(i))){
                    Properties.freeBlockIDs.add(Integer.valueOf(i));
                }
            }else{
                if (Properties.freeBlockIDs.contains(Integer.valueOf(i))){
                    Properties.freeBlockIDs.remove(Integer.valueOf(i));
                }
            }
        }
        if (Properties.freeBlockIDs.isEmpty()){
            throw new RuntimeException("The CCM PropertyHandler has not been able to find any free block IDs");
        }
    }

    public void updateItemIDs(final boolean clear)
    {
        if (clear){
            Properties.freeItemIDs.clear();
        }
        for (int i = this.baseItemID; i < Item.itemsList.length; i++){
            if (Item.itemsList[i] == null){
                if (!Properties.freeItemIDs.contains(Integer.valueOf(i))){
                    Properties.freeItemIDs.add(Integer.valueOf(i));
                }
            }else{
                if (Properties.freeItemIDs.contains(Integer.valueOf(i))){
                    Properties.freeItemIDs.remove(Integer.valueOf(i));
                }
            }
        }
        if (Properties.freeItemIDs.isEmpty()){
            throw new RuntimeException("The CCM PropertyHandler has not been able to find any free item IDs");
        }
    }

    public int getBlockId(final String name)
    {
        if (Properties.knownBlockIds.containsKey(name)){
            final Property ret = Properties.knownBlockIds.get(name);

            if (ret == null){
                return -1;
            }
            return ret.getInt();
        }else{
            if (Properties.freeBlockIDs.isEmpty()){
                this.updateItemIDs(false);
                return Properties.freeBlockIDs.remove(0);
            }else{
                return Properties.freeBlockIDs.remove(0);
            }
        }
    }

    public int getItemId(final String name)
    {
        if (Properties.knownItemIds.containsKey(name)){
            final Property ret = Properties.knownItemIds.get(name);

            if (ret == null){
                return -1;
            }
            return ret.getInt();
        }else{
            if (Properties.freeItemIDs.isEmpty()){
                this.updateItemIDs(false);
                return Properties.freeItemIDs.remove(0);
            }else{
                return Properties.freeItemIDs.remove(0);
            }
        }
    }

    public int get(final String category, final String key, final int defaultValue)
    {

        return this.config.get(category, key, defaultValue).getInt();
    }

    public boolean get(final String category, final String key, final boolean defaultValue)
    {

        return this.config.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    public String get(final String category, final String key, final String defaultValue)
    {

        return this.config.get(category, key, defaultValue).getString();
    }

    public Property getProperty(final String category, final String key, final int defaultValue)
    {

        return this.config.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final boolean defaultValue)
    {

        return this.config.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final String defaultValue)
    {

        return this.config.get(category, key, defaultValue);
    }

    public ConfigCategory getCategory(final String category)
    {

        return this.config.getCategory(category);
    }

    public boolean hasCategory(final String category)
    {

        return this.config.hasCategory(category);
    }

    public boolean hasKey(final String category, final String key)
    {

        return this.config.hasKey(category, key);
    }

    public void save()
    {
        if (this.config.hasChanged()){
            this.config.save();
        }
    }
}