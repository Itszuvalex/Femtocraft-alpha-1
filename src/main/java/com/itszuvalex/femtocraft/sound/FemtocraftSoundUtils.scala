package com.itszuvalex.femtocraft.sound

import net.minecraft.client.Minecraft
import net.minecraft.client.audio.PositionedSoundRecord
import net.minecraft.util.ResourceLocation

/**
 * Created by Christopher Harris (Itszuvalex) on 10/20/14.
 */
object FemtocraftSoundUtils {
  private val clickLocation = new ResourceLocation("gui.button.press")

  def playClickSound() = Minecraft.getMinecraft.getSoundHandler.playSound(PositionedSoundRecord.func_147674_a(clickLocation, 1.0F))

}
