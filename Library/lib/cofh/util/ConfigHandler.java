package lib.cofh.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * This is effectively a wrapper for Forge Configurations. It allows for easier manipulation of
 * Config files.
 * 
 * @author King Lemming
 */
@SuppressWarnings(
{ "unchecked", "rawtypes" })
public class ConfigHandler
{

    ArrayList<String>[]       blockEntries   = new ArrayList[3];

    ArrayList<String>[]       itemEntries    = new ArrayList[3];

    TreeMap<String, Property> blockIds       = new TreeMap();

    TreeMap<String, Property> itemIds        = new TreeMap();

    int                       blockIdCounter = 0;

    int                       itemIdCounter  = 0;

    int                       moduleCounter  = 0;

    Set                       assignedIds    = new HashSet();

    Configuration             modConfiguration;

    String                    modVersion;

    int                       blockIdStart   = 1000;

    int                       itemIdStart    = 10000;

    public ConfigHandler(final String version)
    {

        this.modVersion = version;
        for (int i = 0; i < this.blockEntries.length; i++){
            this.blockEntries[i] = new ArrayList();
        }
        for (int i = 0; i < this.itemEntries.length; i++){
            this.itemEntries[i] = new ArrayList();
        }
    }

    public ConfigHandler(final String version,
                         final int blockStart,
                         final int itemStart)
    {

        this.modVersion = version;
        this.blockIdStart = blockStart;
        this.itemIdStart = itemStart;

        for (int i = 0; i < this.blockEntries.length; i++){
            this.blockEntries[i] = new ArrayList();
        }
        for (int i = 0; i < this.itemEntries.length; i++){
            this.itemEntries[i] = new ArrayList();
        }
    }

    public void setConfiguration(final Configuration config)
    {

        this.modConfiguration = config;
        this.modConfiguration.load();
    }

    public void addBlockEntry(final String name)
    {

        this.addBlockEntry(name, 0);
    }

    public void addItemEntry(final String name)
    {

        this.addItemEntry(name, 0);
    }

    public void addBlockEntry(final String name, final int level)
    {

        this.blockEntries[level].add(name);
        this.blockIdCounter++;
    }

    public void addItemEntry(final String name, final int level)
    {

        this.itemEntries[level].add(name);
        this.itemIdCounter++;
    }

    public int getBlockId(final String name)
    {

        final Property ret = this.blockIds.get(name);

        if (ret == null){
            return -1;
        }
        return ret.getInt();
    }

    public int getItemId(final String name)
    {

        final Property ret = this.itemIds.get(name);

        if (ret == null){
            return -1;
        }
        return ret.getInt();
    }

    public int get(final String category, final String key, final int defaultValue)
    {

        return this.modConfiguration.get(category, key, defaultValue).getInt();
    }

    public boolean get(final String category, final String key, final boolean defaultValue)
    {

        return this.modConfiguration.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    public String get(final String category, final String key, final String defaultValue)
    {

        return this.modConfiguration.get(category, key, defaultValue).getString();
    }

    public Property getProperty(final String category, final String key, final int defaultValue)
    {

        return this.modConfiguration.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final boolean defaultValue)
    {

        return this.modConfiguration.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final String defaultValue)
    {

        return this.modConfiguration.get(category, key, defaultValue);
    }

    public ConfigCategory getCategory(final String category)
    {

        return this.modConfiguration.getCategory(category);
    }

    public boolean hasCategory(final String category)
    {

        return this.modConfiguration.hasCategory(category);
    }

    public boolean hasKey(final String category, final String key)
    {

        return this.modConfiguration.hasKey(category, key);
    }

    public void init()
    {

        // get ids for existing blocks
        for (final ArrayList<String> blockEntrie : this.blockEntries){
            for (final String entry : blockEntrie){
                if (this.modConfiguration.hasKey(Configuration.CATEGORY_BLOCK, entry)){
                    final int existingId = this.modConfiguration.getCategory(Configuration.CATEGORY_BLOCK).getValues().get(entry).getInt();
                    this.assignedIds.add(existingId);
                    this.blockIds.put(entry, this.modConfiguration.getBlock(entry, existingId));
                }
            }
        }
        // get ids for new blocks
        for (final ArrayList<String> blockEntrie : this.blockEntries){
            for (final String entry : blockEntrie){
                if (!this.modConfiguration.hasKey(Configuration.CATEGORY_BLOCK, entry)){
                    boolean idFound = false;
                    for (int j = this.blockIdStart; (j < (this.blockIdStart + this.blockIdCounter)) && !idFound; ++j){
                        if (!this.assignedIds.contains(j)){
                            this.assignedIds.add(j);
                            this.blockIds.put(entry, this.modConfiguration.getBlock(entry, j));
                            idFound = true;
                        }
                    }
                }
            }
        }
        // get ids for existing items
        for (final ArrayList<String> itemEntrie : this.itemEntries){
            for (final String entry : itemEntrie){
                if (this.modConfiguration.hasKey(Configuration.CATEGORY_ITEM, entry)){
                    final int existingId = this.modConfiguration.getCategory(Configuration.CATEGORY_ITEM).getValues().get(entry).getInt();
                    this.assignedIds.add(existingId);
                    this.itemIds.put(entry, this.modConfiguration.getItem(entry, existingId));
                }
            }
        }
        // get ids for new items
        for (final ArrayList<String> itemEntrie : this.itemEntries){
            for (final String entry : itemEntrie){
                if (!this.modConfiguration.hasKey(Configuration.CATEGORY_ITEM, entry)){

                    boolean idFound = false;
                    for (int j = this.itemIdStart; (j < (this.itemIdStart + this.itemIdCounter)) && !idFound; ++j){
                        if (!this.assignedIds.contains(j)){

                            this.assignedIds.add(j);
                            this.itemIds.put(entry, this.modConfiguration.getItem(entry, j));
                            idFound = true;
                        }
                    }
                }
            }
        }
        this.modConfiguration.save();
    }

    public void save()
    {

        this.modConfiguration.save();
    }

    public boolean renameProperty(final String category, final String key, final String newCategory, final String newKey, final boolean forceValue)
    {

        if (this.modConfiguration.hasKey(category, key)){
            final Property prop = this.modConfiguration.getCategory(category).get(key);

            if (prop.isIntValue()){
                final int value = this.modConfiguration.getCategory(category).getValues().get(key).getInt();
                this.removeProperty(category, key);

                if (forceValue){
                    this.removeProperty(newCategory, newKey);
                }
                this.modConfiguration.get(newCategory, newKey, value);
            }else if (prop.isBooleanValue()){
                final boolean value = this.modConfiguration.getCategory(category).getValues().get(key).getBoolean(false);
                this.removeProperty(category, key);

                if (forceValue){
                    this.removeProperty(newCategory, newKey);
                }
                this.modConfiguration.get(newCategory, newKey, value);
            }else if (prop.isDoubleValue()){
                final double value = this.modConfiguration.getCategory(category).getValues().get(key).getDouble(0.0);
                this.removeProperty(category, key);

                if (forceValue){
                    this.removeProperty(newCategory, newKey);
                }
                this.modConfiguration.get(newCategory, newKey, value);
            }else{
                final String value = this.modConfiguration.getCategory(category).getValues().get(key).getString();
                this.removeProperty(category, key);

                if (forceValue){
                    this.removeProperty(newCategory, newKey);
                }
                this.modConfiguration.get(newCategory, newKey, value);
            }
            return true;
        }
        return false;
    }

    public boolean removeProperty(final String category, final String key)
    {

        if (!this.modConfiguration.hasKey(category, key)){
            return false;
        }
        this.modConfiguration.getCategory(category).remove(key);
        return true;
    }

    public boolean renameCategory(final String category, final String newCategory)
    {

        if (!this.modConfiguration.hasCategory(category)){
            return false;
        }
        for (final Property prop : this.modConfiguration.getCategory(category).values()){
            this.renameProperty(category, prop.getName(), newCategory, prop.getName(), true);
        }
        this.removeCategory(category);
        return true;
    }

    public boolean removeCategory(final String category)
    {

        if (!this.modConfiguration.hasCategory(category)){
            return false;
        }
        this.modConfiguration.removeCategory(this.modConfiguration.getCategory(category));
        return true;
    }

    public void cleanUp(final boolean delConfig)
    {

        this.removeProperty("general", "version");
        this.removeProperty("general", "Version");
        this.get("general", "Version", this.modVersion);

        this.modConfiguration.save();

        for (final ArrayList<String> blockEntrie : this.blockEntries){
            blockEntrie.clear();
        }
        this.blockEntries = null;

        for (final ArrayList<String> itemEntrie : this.itemEntries){
            itemEntrie.clear();
        }
        this.itemEntries = null;

        this.blockIds.clear();
        this.itemIds.clear();
        this.assignedIds.clear();

        if (delConfig){
            this.modConfiguration = null;
        }
    }

}
