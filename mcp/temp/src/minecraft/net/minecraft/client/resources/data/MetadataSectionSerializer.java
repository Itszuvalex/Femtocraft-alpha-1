package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public interface MetadataSectionSerializer extends JsonDeserializer {

   String func_110483_a();
}
