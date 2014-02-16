package femtocraft.power.render;

import femtocraft.Femtocraft;
import femtocraft.models.ModelOrbitalEqualizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderNullEqualizer extends TileEntitySpecialRenderer {

	//The model of your block
	private final ModelOrbitalEqualizer model;

	public RenderNullEqualizer() {
		model = new ModelOrbitalEqualizer();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Femtocraft.ID.toLowerCase(),
				"textures/blocks/nullEqualizer.png"));
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		model.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
