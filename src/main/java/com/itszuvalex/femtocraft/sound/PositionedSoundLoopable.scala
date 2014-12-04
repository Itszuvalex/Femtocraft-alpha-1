package com.itszuvalex.femtocraft.sound

import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.util.ResourceLocation

/**
 * Created by Christopher Harris (Itszuvalex) on 10/22/14.
 */
@SideOnly(Side.CLIENT)
class PositionedSoundLoopable(val location: ResourceLocation, val vol: Float, val pitch: Float, val x: Int, val y: Int, val z: Int, val repeats: Boolean, val repeatDelay: Int, val attenuation: ISound.AttenuationType) extends ISound {
  override def getPositionedSoundLocation: ResourceLocation = location

  override def getRepeatDelay: Int = repeatDelay

  override def getPitch: Float = pitch

  override def getXPosF: Float = x

  override def getVolume: Float = vol

  override def getYPosF: Float = y

  override def getAttenuationType: AttenuationType = attenuation

  override def getZPosF: Float = z

  override def canRepeat: Boolean = repeats
}

