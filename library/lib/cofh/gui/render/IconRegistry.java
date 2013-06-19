package lib.cofh.gui.render;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;

/**
 * Convenient String to Icon map. Allows easy reuse of Icons.
 * 
 * @author King Lemming
 */
public class IconRegistry {
    
    public static Map<String, Icon> icons = new HashMap<String, Icon>();
    
    public static void addIcon(final String iconName, final String iconLocation, final IconRegister ir) {
        
        icons.put(iconName, ir.registerIcon(iconLocation));
    }
    
    public static void addIcon(final String iconName, final Icon icon) {
        
        icons.put(iconName, icon);
    }
    
    public static void addCustomIcon(final String iconName, final String iconLocation, final TextureMap ir) {
        
        final TextureCustom icon = new TextureCustom(iconLocation);
        ir.setTextureEntry(iconLocation, icon);
        icons.put(iconName, icon);
    }
    
    public static Icon getIcon(final String iconName) {
        
        return icons.get(iconName);
    }
    
    public static Icon getIcon(final String iconName, final int iconOffset) {
        
        return icons.get(iconName + iconOffset);
    }
    
}
