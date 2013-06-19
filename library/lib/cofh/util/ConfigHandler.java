package lib.cofh.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * This is effectively a wrapper for Forge Configurations. It allows for easier
 * manipulation of Config files.
 * 
 * @author King Lemming
 */
@SuppressWarnings("unchecked")
public class ConfigHandler {
    
    ArrayList<String>[]       blockEntries   = new ArrayList[3];
    ArrayList<String>[]       itemEntries    = new ArrayList[3];
    
    TreeMap<String, Property> blockIds       = new TreeMap<String, Property>();
    TreeMap<String, Property> itemIds        = new TreeMap<String, Property>();
    
    int                       blockIdCounter = 0;
    int                       itemIdCounter  = 0;
    int                       moduleCounter  = 0;
    
    Set<Integer>              assignedIds    = new HashSet<Integer>();
    
    Configuration             modConfiguration;
    String                    modVersion;
    
    int                       blockIdStart   = 1000;
    int                       itemIdStart    = 10000;
    
    public ConfigHandler(final String version) {
        
        modVersion = version;
        for (int i = 0; i < blockEntries.length; i++) {
            blockEntries[i] = new ArrayList<String>();
        }
        for (int i = 0; i < itemEntries.length; i++) {
            itemEntries[i] = new ArrayList<String>();
        }
    }
    
    public ConfigHandler(final String version, final int blockStart, final int itemStart) {
        
        modVersion = version;
        blockIdStart = blockStart;
        itemIdStart = itemStart;
        
        for (int i = 0; i < blockEntries.length; i++) {
            blockEntries[i] = new ArrayList<String>();
        }
        for (int i = 0; i < itemEntries.length; i++) {
            itemEntries[i] = new ArrayList<String>();
        }
    }
    
    public void setConfiguration(final Configuration config) {
        
        modConfiguration = config;
        modConfiguration.load();
    }
    
    public void addBlockEntry(final String name) {
        
        addBlockEntry(name, 0);
    }
    
    public void addItemEntry(final String name) {
        
        addItemEntry(name, 0);
    }
    
    public void addBlockEntry(final String name, final int level) {
        
        blockEntries[level].add(name);
        blockIdCounter++;
    }
    
    public void addItemEntry(final String name, final int level) {
        
        itemEntries[level].add(name);
        itemIdCounter++;
    }
    
    public int getBlockId(final String name) {
        
        final Property ret = blockIds.get(name);
        
        if (ret == null) {
            return -1;
        }
        return ret.getInt();
    }
    
    public int getItemId(final String name) {
        
        final Property ret = itemIds.get(name);
        
        if (ret == null) {
            return -1;
        }
        return ret.getInt();
    }
    
    public int get(final String category, final String key, final int defaultValue) {
        
        return modConfiguration.get(category, key, defaultValue).getInt();
    }
    
    public boolean get(final String category, final String key, final boolean defaultValue) {
        
        return modConfiguration.get(category, key, defaultValue).getBoolean(defaultValue);
    }
    
    public String get(final String category, final String key, final String defaultValue) {
        
        return modConfiguration.get(category, key, defaultValue).getString();
    }
    
    public Property getProperty(final String category, final String key, final int defaultValue) {
        
        return modConfiguration.get(category, key, defaultValue);
    }
    
    public Property getProperty(final String category, final String key, final boolean defaultValue) {
        
        return modConfiguration.get(category, key, defaultValue);
    }
    
    public Property getProperty(final String category, final String key, final String defaultValue) {
        
        return modConfiguration.get(category, key, defaultValue);
    }
    
    public ConfigCategory getCategory(final String category) {
        
        return modConfiguration.getCategory(category);
    }
    
    public boolean hasCategory(final String category) {
        
        return modConfiguration.hasCategory(category);
    }
    
    public boolean hasKey(final String category, final String key) {
        
        return modConfiguration.hasKey(category, key);
    }
    
    public void init() {
        
        // get ids for existing blocks
        for (final ArrayList<String> blockEntrie : blockEntries) {
            for (final String entry : blockEntrie) {
                if (modConfiguration.hasKey(Configuration.CATEGORY_BLOCK, entry)) {
                    final int existingId = modConfiguration
                            .getCategory(Configuration.CATEGORY_BLOCK).getValues().get(entry)
                            .getInt();
                    assignedIds.add(existingId);
                    blockIds.put(entry, modConfiguration.getBlock(entry, existingId));
                }
            }
        }
        // get ids for new blocks
        for (final ArrayList<String> blockEntrie : blockEntries) {
            for (final String entry : blockEntrie) {
                if (!modConfiguration.hasKey(Configuration.CATEGORY_BLOCK, entry)) {
                    boolean idFound = false;
                    for (int j = blockIdStart; (j < (blockIdStart + blockIdCounter)) && !idFound; ++j) {
                        if (!assignedIds.contains(j)) {
                            assignedIds.add(j);
                            blockIds.put(entry, modConfiguration.getBlock(entry, j));
                            idFound = true;
                        }
                    }
                }
            }
        }
        // get ids for existing items
        for (final ArrayList<String> itemEntrie : itemEntries) {
            for (final String entry : itemEntrie) {
                if (modConfiguration.hasKey(Configuration.CATEGORY_ITEM, entry)) {
                    final int existingId = modConfiguration
                            .getCategory(Configuration.CATEGORY_ITEM).getValues().get(entry)
                            .getInt();
                    assignedIds.add(existingId);
                    itemIds.put(entry, modConfiguration.getItem(entry, existingId));
                }
            }
        }
        // get ids for new items
        for (final ArrayList<String> itemEntrie : itemEntries) {
            for (final String entry : itemEntrie) {
                if (!modConfiguration.hasKey(Configuration.CATEGORY_ITEM, entry)) {
                    
                    boolean idFound = false;
                    for (int j = itemIdStart; (j < (itemIdStart + itemIdCounter)) && !idFound; ++j) {
                        if (!assignedIds.contains(j)) {
                            
                            assignedIds.add(j);
                            itemIds.put(entry, modConfiguration.getItem(entry, j));
                            idFound = true;
                        }
                    }
                }
            }
        }
        modConfiguration.save();
    }
    
    public void save() {
        
        modConfiguration.save();
    }
    
    public boolean renameProperty(final String category, final String key,
            final String newCategory, final String newKey, final boolean forceValue) {
        
        if (modConfiguration.hasKey(category, key)) {
            final Property prop = modConfiguration.getCategory(category).get(key);
            
            if (prop.isIntValue()) {
                final int value = modConfiguration.getCategory(category).getValues().get(key)
                        .getInt();
                removeProperty(category, key);
                
                if (forceValue) {
                    removeProperty(newCategory, newKey);
                }
                modConfiguration.get(newCategory, newKey, value);
            } else if (prop.isBooleanValue()) {
                final boolean value = modConfiguration.getCategory(category).getValues().get(key)
                        .getBoolean(false);
                removeProperty(category, key);
                
                if (forceValue) {
                    removeProperty(newCategory, newKey);
                }
                modConfiguration.get(newCategory, newKey, value);
            } else if (prop.isDoubleValue()) {
                final double value = modConfiguration.getCategory(category).getValues().get(key)
                        .getDouble(0.0);
                removeProperty(category, key);
                
                if (forceValue) {
                    removeProperty(newCategory, newKey);
                }
                modConfiguration.get(newCategory, newKey, value);
            } else {
                final String value = modConfiguration.getCategory(category).getValues().get(key)
                        .getString();
                removeProperty(category, key);
                
                if (forceValue) {
                    removeProperty(newCategory, newKey);
                }
                modConfiguration.get(newCategory, newKey, value);
            }
            return true;
        }
        return false;
    }
    
    public boolean removeProperty(final String category, final String key) {
        
        if (!modConfiguration.hasKey(category, key)) {
            return false;
        }
        modConfiguration.getCategory(category).remove(key);
        return true;
    }
    
    public boolean renameCategory(final String category, final String newCategory) {
        
        if (!modConfiguration.hasCategory(category)) {
            return false;
        }
        for (final Property prop : modConfiguration.getCategory(category).values()) {
            renameProperty(category, prop.getName(), newCategory, prop.getName(), true);
        }
        removeCategory(category);
        return true;
    }
    
    public boolean removeCategory(final String category) {
        
        if (!modConfiguration.hasCategory(category)) {
            return false;
        }
        modConfiguration.removeCategory(modConfiguration.getCategory(category));
        return true;
    }
    
    public void cleanUp(final boolean delConfig) {
        
        removeProperty("general", "version");
        removeProperty("general", "Version");
        get("general", "Version", modVersion);
        
        modConfiguration.save();
        
        for (final ArrayList<String> blockEntrie : blockEntries) {
            blockEntrie.clear();
        }
        blockEntries = null;
        
        for (final ArrayList<String> itemEntrie : itemEntries) {
            itemEntrie.clear();
        }
        itemEntries = null;
        
        blockIds.clear();
        itemIds.clear();
        assignedIds.clear();
        
        if (delConfig) {
            modConfiguration = null;
        }
    }
    
}
