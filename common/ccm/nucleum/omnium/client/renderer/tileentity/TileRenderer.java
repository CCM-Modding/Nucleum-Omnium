/**
 * CCM Modding, Nucleum Omnium
 */
package ccm.nucleum.omnium.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

import ccm.nucleum.omnium.client.model.AdvancedBaseModel;

/**
 * TileRenderer
 * <p>
 * 
 * @author Captain_Shadows
 */
public abstract class TileRenderer extends TileEntitySpecialRenderer
{

    protected AdvancedBaseModel model;

    public abstract void render(final TileEntity tile, final double x, final double y, final double z, final float tick);

    @Override
    public void renderTileEntityAt(final TileEntity tile, final double x, final double y, final double z, final float tick)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        render(tile, x, y, z, tick);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

}