package com.itszuvalex.femtocraft.industry.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.block.DroppableInventory
import com.itszuvalex.femtocraft.industry.tiles.TileEntityModulationLensMount
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 3/1/15.
 */
class BlockModulationLensMount extends TileContainer(Material.iron) with DroppableInventory {
  setCreativeTab(Femtocraft.femtocraftTab)

  override def registerBlockIcons(p_149651_1_ : IIconRegister): Unit = blockIcon = p_149651_1_.registerIcon(Femtocraft.ID + ":" + "BlockModulationLensMount")

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int): TileEntity = new TileEntityModulationLensMount
}
