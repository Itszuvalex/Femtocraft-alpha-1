package femtocraft.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderTest extends TileEntitySpecialRenderer {

	//The model of your block
	private final OrbitalModel model;

	public RenderTest() {
		model = new OrbitalModel();
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
		ResourceLocation textures = (new ResourceLocation("[yourmodidhere]:textures/blocks/TrafficLightPoleRed.png"));
		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		GL11.glPushMatrix();
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		model.render();
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		//		GL11.glDisable(GL11.GL_LIGHTING);
		//		GL11.glPushMatrix();
		//		GL11.glScalef(0.01F, 0.01F, 0.1F);
		//		GL11.glTranslatef((float) x, (float) y + 0.5F, (float) z);
		//		Minecraft.getMinecraft().renderEngine.bindTexture(textures);
		//		model.render();
		//		GL11.glEnable(GL11.GL_LIGHTING);
		//		GL11.glPopMatrix();
	}
}
