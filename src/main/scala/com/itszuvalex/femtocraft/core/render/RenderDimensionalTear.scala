package net.minecraft.client.renderer.tileentity

import java.nio.FloatBuffer
import java.util.Random

import com.itszuvalex.femtocraft.core.tiles.TileEntityDimensionalTear
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.tileentity.RenderDimensionalTear._
import net.minecraft.client.renderer.{ActiveRenderInfo, GLAllocation, Tessellator}
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ResourceLocation
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT) object RenderDimensionalTear {
  private val skyLocation    : ResourceLocation = new ResourceLocation("textures/environment/end_sky.png")
  private val pictureLocation: ResourceLocation = new ResourceLocation("textures/entity/end_portal.png")
  private val random         : Random           = new Random(31100L)
}

@SideOnly(Side.CLIENT) class RenderDimensionalTear extends TileEntitySpecialRenderer {
  private val field_147528_b: FloatBuffer = GLAllocation.createDirectFloatBuffer(16)

  def renderTileEntityAt(p_147500_1_ : TileEntity, p_147500_2_ : Double, p_147500_4_ : Double, p_147500_6_ : Double, p_147500_8_ : Float) {
    this.renderTileEntityAt(p_147500_1_.asInstanceOf[TileEntityDimensionalTear], p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_)
  }

  def renderTileEntityAt(tileEntity: TileEntityDimensionalTear, x: Double, y: Double, z: Double, p_147500_8_ : Float) {

    drawTopFace(tileEntity, x, y, z, p_147500_8_)
  }

  def drawTopFace(tileEntity: TileEntityDimensionalTear, x: Double, y: Double, z: Double, p_147500_8_ : Float): Unit = {
    val f1: Float = this.field_147501_a.field_147560_j.toFloat
    val f2: Float = this.field_147501_a.field_147561_k.toFloat
    val f3: Float = this.field_147501_a.field_147558_l.toFloat
    GL11.glDisable(GL11.GL_LIGHTING)
    random.setSeed(31100L)
    val height: Float = 1

    GL11.glPushMatrix()
    var f5: Float = 16f
    var f6: Float = 0.0625F
    var f7: Float = 1.0F / (f5 + 1.0F)
    this.bindTexture(skyLocation)
    f7 = 0.1F
    f5 = 65.0F
    f6 = 0.125F
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    val f8: Float = (-(y + height.toDouble)).toFloat
    var f9: Float = f8 + ActiveRenderInfo.objectY
    val f10: Float = f8 + f5 + ActiveRenderInfo.objectY
    var f11: Float = f9 / f10
    f11 += (y + height.toDouble).toFloat
    GL11.glTranslatef(f1, f11, f3)
    GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR)
    GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR)
    GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR)
    GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR)
    GL11.glTexGen(GL11.GL_S, GL11.GL_OBJECT_PLANE, this.func_147525_a(1.0F, 0.0F, 0.0F, 0.0F))
    GL11.glTexGen(GL11.GL_T, GL11.GL_OBJECT_PLANE, this.func_147525_a(0.0F, 0.0F, 1.0F, 0.0F))
    GL11.glTexGen(GL11.GL_R, GL11.GL_OBJECT_PLANE, this.func_147525_a(0.0F, 0.0F, 0.0F, 1.0F))
    GL11.glTexGen(GL11.GL_Q, GL11.GL_EYE_PLANE, this.func_147525_a(0.0F, 1.0F, 0.0F, 0.0F))
    GL11.glEnable(GL11.GL_TEXTURE_GEN_S)
    GL11.glEnable(GL11.GL_TEXTURE_GEN_T)
    GL11.glEnable(GL11.GL_TEXTURE_GEN_R)
    GL11.glEnable(GL11.GL_TEXTURE_GEN_Q)
    GL11.glPopMatrix()
    GL11.glMatrixMode(GL11.GL_TEXTURE)
    GL11.glPushMatrix()
    GL11.glLoadIdentity()
    GL11.glTranslatef(0.0F, (Minecraft.getSystemTime % 700000L).toFloat / 700000.0F, 0.0F)
    GL11.glScalef(f6, f6, f6)
    GL11.glTranslatef(0.5F, 0.5F, 0.0F)
    GL11.glRotatef(4321F * 2.0F, 0.0F, 0.0F, 1.0F)
    GL11.glTranslatef(-0.5F, -0.5F, 0.0F)
    GL11.glTranslatef(-f1, -f3, -f2)
    f9 = f8 + ActiveRenderInfo.objectY
    GL11.glTranslatef(ActiveRenderInfo.objectX * f5 / f9, ActiveRenderInfo.objectZ * f5 / f9, -f2)
    val tessellator = Tessellator.instance
    tessellator.startDrawingQuads()
    f11 = random.nextFloat * 0.5F + 0.1F
    var f12 = random.nextFloat * 0.5F + 0.4F
    var f13 = random.nextFloat * 0.5F + 0.5F
    f13 = 1.0F
    f12 = 1.0F
    f11 = 1.0F
    tessellator.setColorRGBA_F(f11 * f7, f12 * f7, f13 * f7, 1.0F)
    //render top
    tessellator.addVertex(x, y + height.toDouble, z)
    tessellator.addVertex(x, y + height.toDouble, z + 1.0D)
    tessellator.addVertex(x + 1.0D, y + height.toDouble, z + 1.0D)
    tessellator.addVertex(x + 1.0D, y + height.toDouble, z)
    //render bot
    //      tessellator.addVertex(p_147500_2_ + 1.0D, p_147500_4_ , p_147500_6_)
    //      tessellator.addVertex(p_147500_2_ + 1.0D, p_147500_4_ , p_147500_6_ + 1.0D)
    //      tessellator.addVertex(p_147500_2_, p_147500_4_ , p_147500_6_ + 1.0D)
    //      tessellator.addVertex(p_147500_2_, p_147500_4_ , p_147500_6_)
    tessellator.draw()
    GL11.glPopMatrix()
    GL11.glMatrixMode(GL11.GL_MODELVIEW)
    GL11.glDisable(GL11.GL_BLEND)
    GL11.glDisable(GL11.GL_TEXTURE_GEN_S)
    GL11.glDisable(GL11.GL_TEXTURE_GEN_T)
    GL11.glDisable(GL11.GL_TEXTURE_GEN_R)
    GL11.glDisable(GL11.GL_TEXTURE_GEN_Q)
    GL11.glEnable(GL11.GL_LIGHTING)

  }

  private def func_147525_a(p_147525_1_ : Float, p_147525_2_ : Float, p_147525_3_ : Float, p_147525_4_ : Float): FloatBuffer = {
    this.field_147528_b.clear
    this.field_147528_b.put(p_147525_1_).put(p_147525_2_).put(p_147525_3_).put(p_147525_4_)
    this.field_147528_b.flip
    this.field_147528_b
  }
}