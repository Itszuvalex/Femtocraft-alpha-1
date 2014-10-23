package com.itszuvalex.femtocraft.sound

import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.client.audio.PositionedSound
import net.minecraft.util.ResourceLocation

/**
 * Created by Christopher Harris (Itszuvalex) on 10/22/14.
 */
class PositionedSoundLoopable(location: ResourceLocation, vol: Float, pitch: Float, x: Int, y: Int, z: Int, repeats: Boolean, repeatDelay: Int, attenuation: AttenuationType) extends PositionedSound(location) {
  this.volume = vol
  this.field_147663_c = pitch
  this.xPosF = x
  this.yPosF = y
  this.zPosF = z
  this.repeat = repeats
  this.field_147665_h = repeatDelay
  this.field_147666_i = attenuation
}
