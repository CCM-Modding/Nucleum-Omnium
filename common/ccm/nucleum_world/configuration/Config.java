package ccm.nucleum_world.configuration;

import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public class Config
{

    public Configuration config;

    public Config(final Configuration config)
    {
        this.config = config;
    }

    public Configuration getConfig()
    {
        return this.config;
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
}