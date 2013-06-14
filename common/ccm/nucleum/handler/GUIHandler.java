package ccm.nucleum.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ccm.nucleum.IMod;
import cpw.mods.fml.common.network.IGuiHandler;

public final class GUIHandler implements IGuiHandler {
    
    private static Map<Integer, Class<? extends GuiContainer>> guiList       = new HashMap<Integer, Class<? extends GuiContainer>>();
    
    private static Map<Integer, Class<? extends Container>>    containerList = new HashMap<Integer, Class<? extends Container>>();
    
    private static GUIHandler                                  instance;
    
    public static GUIHandler instance() {
        if (GUIHandler.instance == null) {
            GUIHandler.instance = new GUIHandler();
        }
        return GUIHandler.instance;
    }
    
    public static void registerGuiServer(final IMod mod, final String guiID,
            final Class<? extends Container> container) {
        final String fixedID = guiID + mod.getName();
        GUIHandler.containerList.put(fixedID.hashCode(), container);
    }
    
    public static void registerGuiClient(final IMod mod, final String guiID,
            final Class<? extends GuiContainer> gui, final Class<? extends Container> container) {
        final String fixedID = guiID + mod.getName();
        GUIHandler.guiList.put(fixedID.hashCode(), gui);
        GUIHandler.containerList.put(fixedID.hashCode(), container);
    }
    
    public static void openGui(final IMod mod, final String guiID, final EntityPlayer player,
            final World world, final int x, final int y, final int z) {
        final String fixedID = guiID + mod.getName();
        player.openGui(mod, fixedID.hashCode(), world, x, y, z);
    }
    
    @Override
    public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world,
            final int x, final int y, final int z) {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        Container container = null;
        try {
            container = GUIHandler.containerList.get(ID)
                    .getConstructor(InventoryPlayer.class, TileEntity.class)
                    .newInstance(player.inventory, te);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return container;
    }
    
    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world,
            final int x, final int y, final int z) {
        final TileEntity te = world.getBlockTileEntity(x, y, z);
        GuiContainer gui = null;
        try {
            gui = GUIHandler.guiList.get(ID)
                    .getConstructor(InventoryPlayer.class, TileEntity.class)
                    .newInstance(player.inventory, te);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return gui;
    }
}