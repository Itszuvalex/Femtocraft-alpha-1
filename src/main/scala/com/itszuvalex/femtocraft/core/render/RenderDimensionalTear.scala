package net.minecraft.client.renderer.tileentity

import java.nio.FloatBuffer
import java.util.Random

import com.itszuvalex.femtocraft.core.tiles.TileEntityDimensionalTear
import cpw.mods.fml.relauncher.{Side, SideOnly}
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
    drawBotFace(tileEntity, x, y, z, p_147500_8_)
    drawEastFace(tileEntity, x, y, z, p_147500_8_)
  }

  def drawTopFace(tileEntity: TileEntityDimensionalTear, x: Double, y: Double, z: Double, p_147500_8_ : Float): Unit = {
    val rendererDispatcherX: Float = this.field_147501_a.field_147560_j.toFloat
    val rendererDispatcherZ: Float = this.field_147501_a.field_147561_k.toFloat
    val rendererDispatcherY: Float = this.field_147501_a.field_147558_l.toFloat
    GL11.glDisable(GL11.GL_LIGHTING)
    random.setSeed(31100L)
    val height: Float = 1

    GL11.glPushMatrix()
    this.bindTexture(skyLocation)
    val greyScaleColorAmount = 0.1F
    val renderOffsetRatioConst = 5.0F
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    val worldY = (-(y + height.toDouble)).toFloat
    val renderY = worldY + ActiveRenderInfo.objectY
    val fakeCameraY = renderY + renderOffsetRatioConst
    var ytrans = renderY / fakeCameraY
    ytrans += (y + height.toDouble).toFloat

    /*
     * Distance of the texture from the camera is difference in ytrans and rendererDispatcherY
     */
    GL11.glTranslatef(rendererDispatcherX, ytrans, rendererDispatcherZ)
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
    GL11.glTranslatef(-rendererDispatcherX, -rendererDispatcherY, -rendererDispatcherZ)
    GL11.glTranslatef(ActiveRenderInfo.objectX * renderOffsetRatioConst / renderY, ActiveRenderInfo.objectZ * renderOffsetRatioConst / renderY, -rendererDispatcherZ)
    val tessellator = Tessellator.instance
    tessellator.startDrawingQuads()
    val blue = 1.0F
    val green = 1.0F
    val red = 1.0F
    tessellator.setColorRGBA_F(red * greyScaleColorAmount, green * greyScaleColorAmount, blue * greyScaleColorAmount, 1.0F)
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

  def drawBotFace(tileEntity: TileEntityDimensionalTear, x: Double, y: Double, z: Double, p_147500_8_ : Float): Unit = {
    val rendererDispatcherX: Float = this.field_147501_a.field_147560_j.toFloat
    val rendererDispatcherZ: Float = this.field_147501_a.field_147561_k.toFloat
    val rendererDispatcherY: Float = this.field_147501_a.field_147558_l.toFloat
    GL11.glDisable(GL11.GL_LIGHTING)
    random.setSeed(31100L)

    GL11.glPushMatrix()
    this.bindTexture(skyLocation)
    val greyScaleColorAmount = 0.1F
    val renderOffsetRatioConst = 5.0F
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    /*
      (y +
     */
    val worldY = y.toFloat
    val renderY = worldY - ActiveRenderInfo.objectY
    val fakeCameraY = renderY + renderOffsetRatioConst
    var ytrans = renderY / fakeCameraY
    ytrans += y.toFloat

    /*
       Distance of the texture from the camera is difference in ytrans and rendererDispatcherY
     */
    GL11.glTranslatef(rendererDispatcherX, ytrans, rendererDispatcherZ)
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
    GL11.glTranslatef(-rendererDispatcherX, -rendererDispatcherY, -rendererDispatcherZ)
    GL11.glTranslatef(ActiveRenderInfo.objectX * renderOffsetRatioConst / renderY, ActiveRenderInfo.objectZ * renderOffsetRatioConst / renderY, -rendererDispatcherZ)
    val tessellator = Tessellator.instance
    tessellator.startDrawingQuads()
    val blue = 1.0F
    val green = 1.0F
    val red = 1.0F
    tessellator.setColorRGBA_F(red * greyScaleColorAmount, green * greyScaleColorAmount, blue * greyScaleColorAmount, 1.0F)
    //render bot
    tessellator.addVertex(x + 1.0D, y, z)
    tessellator.addVertex(x + 1.0D, y, z + 1.0D)
    tessellator.addVertex(x, y, z + 1.0D)
    tessellator.addVertex(x, y, z)
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

  def drawEastFace(tileEntity: TileEntityDimensionalTear, x: Double, y: Double, z: Double, p_147500_8_ : Float): Unit = {
    val rendererDispatcherX: Float = this.field_147501_a.field_147560_j.toFloat
    val rendererDispatcherZ: Float = this.field_147501_a.field_147561_k.toFloat
    val rendererDispatcherY: Float = this.field_147501_a.field_147558_l.toFloat
    GL11.glDisable(GL11.GL_LIGHTING)
    random.setSeed(31100L)
    val offset: Float = 1

    GL11.glPushMatrix()
    this.bindTexture(skyLocation)
    val greyScaleColorAmount = 0.1F
    val renderOffsetRatioConst = 5.0F
    GL11.glEnable(GL11.GL_BLEND)
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
    val worldX = (-(x + offset.toDouble)).toFloat
    val renderX = worldX + ActiveRenderInfo.objectX
    val fakeCameraX = renderX + renderOffsetRatioConst
    var xtrans = renderX / fakeCameraX
    xtrans += (x + offset.toDouble).toFloat

    /*
     * Distance of the texture from the camera is difference in xtrans and rendererDispatcherY
     */
    GL11.glTranslatef(xtrans, rendererDispatcherY, rendererDispatcherZ)
    GL11.glTexGeni(GL11.GL_S, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR)
    GL11.glTexGeni(GL11.GL_T, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR)
    GL11.glTexGeni(GL11.GL_R, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_EYE_LINEAR)
    GL11.glTexGeni(GL11.GL_Q, GL11.GL_TEXTURE_GEN_MODE, GL11.GL_OBJECT_LINEAR)
    GL11.glTexGen(GL11.GL_S, GL11.GL_OBJECT_PLANE, this.func_147525_a(1.0F, 0.0F, 0.0F, 0.0F))
    GL11.glTexGen(GL11.GL_T, GL11.GL_OBJECT_PLANE, this.func_147525_a(0.0F, 0.0F, 1.0F, 0.0F))
    GL11.glTexGen(GL11.GL_R, GL11.GL_EYE_PLANE, this.func_147525_a(0.0F, 0.0F, 0.0F, 1.0F))
    GL11.glTexGen(GL11.GL_Q, GL11.GL_OBJECT_PLANE, this.func_147525_a(0.0F, 1.0F, 0.0F, 0.0F))
    GL11.glEnable(GL11.GL_TEXTURE_GEN_S)
    GL11.glEnable(GL11.GL_TEXTURE_GEN_T)
    GL11.glEnable(GL11.GL_TEXTURE_GEN_R)
    GL11.glEnable(GL11.GL_TEXTURE_GEN_Q)
    GL11.glPopMatrix()
    GL11.glMatrixMode(GL11.GL_TEXTURE)
    GL11.glPushMatrix()
    GL11.glLoadIdentity()
    GL11.glTranslatef(-rendererDispatcherX, -rendererDispatcherY, -rendererDispatcherZ)
    GL11.glTranslatef(-rendererDispatcherX, ActiveRenderInfo.objectZ * renderOffsetRatioConst / renderX, ActiveRenderInfo.objectY * renderOffsetRatioConst / renderX)
    val tessellator = Tessellator.instance
    tessellator.startDrawingQuads()
    val blue = 1.0F
    val green = 1.0F
    val red = 1.0F
    tessellator.setColorRGBA_F(red * greyScaleColorAmount, green * greyScaleColorAmount, blue * greyScaleColorAmount, 1.0F)
    //render top
    tessellator.addVertex(x + offset.toDouble, y, z)
    tessellator.addVertex(x + offset.toDouble, y + 1.0, z)
    tessellator.addVertex(x + offset.toDouble, y + 1.0, z + 1.0D)
    tessellator.addVertex(x + offset.toDouble, y, z + 1.0)
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
}