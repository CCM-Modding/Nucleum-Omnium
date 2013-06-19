package ccm.nucleum_omnium.configuration;

import java.io.File;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * This class offers advanced configurations capabilities, to "Enhance" normal Forge Configurations.
 */
public class AdvConfiguration extends Configuration {
    
    public AdvConfiguration() {
        super();
    }
    
    /**
     * Creates a configuration file for the file given in parameter.
     * 
     * @param configFile
     *            The File to make a Configuration File out of
     */
    public AdvConfiguration(final File configFile) {
        super(configFile);
    }
    
    /**
     * Creates a configuration file for the file given in parameter. And makes sure that the categories are case sensitive
     * 
     * @param configFile
     *            The File to make a Configuration File out of
     * @param caseSensitiveCustomCategories
     *            True if you want your custom categories to be case sensitive, False otherwise
     */
    public AdvConfiguration(final File configFile, final boolean caseSensitiveCustomCategories) {
        super(configFile, caseSensitiveCustomCategories);
    }
    
    /**
     * Gets or creates a block id property. If the block id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param key
     *            Normally a String containig the name of the block
     * @param defaultID
     *            the ID to be checked
     * @return A {@link Property} containig the ID of the block
     */
    @Override
    public Property getBlock(final String key, final int defaultID) {
        return this.getBlock(Configuration.CATEGORY_BLOCK, key, defaultID, null);
    }
    
    /**
     * Gets or creates a block id property. If the block id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param key
     *            Normally a String containig the name of the block
     * @param defaultID
     *            the ID to be checked
     * @param comment
     *            A comment about the block
     * @return A {@link Property} containig the ID of the block
     */
    @Override
    public Property getBlock(final String key, final int defaultID, final String comment) {
        return this.getBlock(Configuration.CATEGORY_BLOCK, key, defaultID, comment);
    }
    
    /**
     * Gets or creates a block id property. If the block id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param category
     *            A custom Category in wich to put the block in
     * @param key
     *            Normally a String containig the name of the block
     * @param defaultID
     *            the ID to be checked
     * @return A {@link Property} containig the ID of the block
     */
    @Override
    public Property getBlock(final String category, final String key, final int defaultID) {
        return super.getBlock(category, key, getFreeID(defaultID, Block.blocksList));
    }
    
    /**
     * Gets or creates a block id property. If the block id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param category
     *            A custom Category in wich to put the block in
     * @param key
     *            Normally a String containig the name of the block
     * @param defaultID
     *            the ID to be checked
     * @param comment
     *            A comment about the block
     * @return A {@link Property} containig the ID of the block
     */
    @Override
    public Property getBlock(final String category, final String key, final int defaultID, final String comment) {
        return super.getBlock(category, key, getFreeID(defaultID, Block.blocksList), comment);
    }
    
    /**
     * Special version of getBlock to be used when you want to garentee the ID you get is below 256 This should ONLY be used by mods who do low level terrain generation, or ones
     * that add new biomes. EXA: ExtraBiomesXL, Biomes o' Plennty Specifically, if your block is used BEFORE the Chunk is created, and placed in the terrain byte array directly. If
     * you add a new biome and you set the top/filler block, they need to be <256, nothing else. If you're adding a new ore, DON'T call this function. Normal mods such as '50 new
     * ores' do not need to be below 256 so should use the normal getBlock
     * 
     * @param category
     *            A custom Category in wich to put the block in
     * @param key
     *            Normally a String containig the name of the block
     * @param defaultID
     *            the ID to be checked, in here it is checked to be less than 256, otherwise the check begins at 1
     * @param comment
     *            A comment about the block
     * @return A {@link Property} containig the ID of the block
     */
    @Override
    public Property getTerrainBlock(final String category, final String key, final int defaultID, final String comment) {
        return super.getTerrainBlock(category, key, getTerrainFreeID(defaultID, Block.blocksList), comment);
    }
    
    /**
     * Gets or creates a item id property. If the item id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param key
     *            Normally a String containig the name of the item
     * @param defaultID
     *            the ID to be checked
     * @return A {@link Property} containig the ID of the item
     */
    @Override
    public Property getItem(final String key, final int defaultID) {
        return this.getItem(Configuration.CATEGORY_ITEM, key, defaultID, null);
    }
    
    /**
     * Gets or creates a item id property. If the item id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param key
     *            Normally a String containig the name of the item
     * @param defaultID
     *            the ID to be checked
     * @param comment
     *            A comment about the item
     * @return A {@link Property} containig the ID of the item
     */
    @Override
    public Property getItem(final String key, final int defaultID, final String comment) {
        return this.getItem(Configuration.CATEGORY_ITEM, key, defaultID, comment);
    }
    
    /**
     * Gets or creatse a item id property. If the item id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param category
     *            A custom Category in wich to put the item in
     * @param key
     *            Normally a String containig the name of the item
     * @param defaultID
     *            the ID to be checked
     * @return A {@link Property} containig the ID of the item
     */
    @Override
    public Property getItem(final String category, final String key, final int defaultID) {
        return this.getItem(category, key, defaultID, null);
    }
    
    /**
     * Gets or creates a item id property. If the item id property key is already in the configuration, then it will be used. Otherwise, defaultId will be used, except if already
     * taken, in which case this will try to determine a free default id.
     * 
     * @param category
     *            A custom Category in wich to put the item in
     * @param key
     *            Normally a String containig the name of the item
     * @param defaultID
     *            the ID to be checked
     * @param comment
     *            A comment about the item
     * @return A {@link Property} containig the ID of the item
     */
    @Override
    public Property getItem(final String category, final String key, final int defaultID, final String comment) {
        
        return super.getItem(category, key, getFreeID(defaultID, Item.itemsList), comment);
    }
    
    /**
     * Gets a free ID by checking against the array passed in
     * 
     * @param defaultID
     *            The ID in which to begin the search
     * @param array
     *            The array to search in; Typically {@code Item.itemsList} or {@code Block.blocksList}
     */
    private int getFreeID(final int defaultID, final Object[] array) {
        if (array[defaultID] == null) {
            return defaultID;
        } else {
            int freeID = defaultID;
            for (int i = freeID; i < array.length; i++) {
                if (array[i] == null) {
                    freeID = i;
                    break;
                }
            }
            return freeID;
        }
    }
    
    /**
     * Special version of getFreeID used for and only for terrain gen ids
     * 
     * @param defaultID
     *            The ID in which to begin the search. IT SHOULD BE < 256, if it is not it will be set to 1
     * @param array
     *            The array to search in; Typically {@code Block.blocksList}
     */
    private int getTerrainFreeID(final int defaultID, final Object[] array) {
        if ((defaultID < 256) && (array[defaultID] == null)) {
            return defaultID;
        } else {
            int freeID;
            if (defaultID < 256) {
                freeID = defaultID;
            } else {
                freeID = 1;
            }
            for (int i = freeID; i < array.length; i++) {
                if (array[i] == null) {
                    if (i < 256) {
                        freeID = i;
                        break;
                    }
                }
            }
            return freeID;
        }
    }
    
    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public int getProp(final String category, final String key, final int defaultValue) {
        
        return super.get(category, key, defaultValue).getInt();
    }
    
    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getProp(final String category, final String key, final boolean defaultValue) {
        
        return super.get(category, key, defaultValue).getBoolean(defaultValue);
    }
    
    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProp(final String category, final String key, final String defaultValue) {
        
        return super.get(category, key, defaultValue).getString();
    }
    
    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public Property getProperty(final String category, final String key, final int defaultValue) {
        
        return super.get(category, key, defaultValue);
    }
    
    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public Property getProperty(final String category, final String key, final boolean defaultValue) {
        
        return super.get(category, key, defaultValue);
    }
    
    /**
     * @param category
     * @param key
     * @param defaultValue
     * @return
     */
    public Property getProperty(final String category, final String key, final String defaultValue) {
        
        return super.get(category, key, defaultValue);
    }
    
    /**
     * If a pre-existing Configuration file didn't exist it creates a new one. If there were changes to the existing Configuration file, It saves them. Otherwise it doesn't do
     * anything
     */
    @Override
    public void save() {
        if (super.hasChanged()) {
            super.save();
        }
    }
}