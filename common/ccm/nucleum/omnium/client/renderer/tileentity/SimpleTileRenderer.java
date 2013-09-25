/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.client.model.SimpleModel;

/**
 * TileRenderer
 * <p>
 * Does all the GL_LIGHTING and Matrix things for you
 * 
 * @author Captain_Shadows
 */
public abstract class SimpleTileRenderer extends TileEntitySpecialRenderer
{

    protected SimpleModel model;

    @Override
    public void renderTileEntityAt(final TileEntity tile, final double x, final double y, final double z, final float tick)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        render(tile, x, y, z, tick);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public abstract void render(final TileEntity tile, final double x, final double y, final double z, final float tick);
}