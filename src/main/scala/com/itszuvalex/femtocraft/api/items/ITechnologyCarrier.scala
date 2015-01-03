package com.itszuvalex.femtocraft.api.items

import java.util

import com.itszuvalex.femtocraft.api.items.ITechnologyCarrier._
import com.itszuvalex.femtocraft.api.{EnumTechLevel, FemtocraftAPI}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraft.world.World

object ITechnologyCarrier {
  val researchKey = "techName"
}

/**
 * @author Itszuvalex (Christopher Harris)
 *         <p/>
 *         Interface for an Item that carries a Femtocraft Technology.
 */
trait ITechnologyCarrier extends Item {
  /**
   *
   * @param stack
   * @return
   */
  def getTechnologyLevel(stack: ItemStack): EnumTechLevel


  override def onItemRightClick(par1ItemStack: ItemStack, par2World: World,
                                par3EntityPlayer: EntityPlayer): ItemStack = {
    val rm = FemtocraftAPI.getResearchManager
    if (rm != null) {
      val pr = rm.getPlayerResearch(par3EntityPlayer.getCommandSenderName)
      val tech: String = getTechnology(par1ItemStack)
      if (!(tech == null || tech.isEmpty)) {
        if (!par2World.isRemote) {
          if (pr.researchTechnology(tech, force = false)) {
            val rt = rm.getTechnology(tech)
            if (rt != null && rt.getDiscoverItem != null) {
              return rt.getDiscoverItem.copy
            }
            par1ItemStack.stackSize = 0
          }
        }
      }
    }
    super.onItemRightClick(par1ItemStack, par2World, par3EntityPlayer)
  }

  @SideOnly(Side.CLIENT)
  @SuppressWarnings(Array("unchecked"))
  override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: util.List[_],
                              par4: Boolean) {
    super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4)
    val compound = par1ItemStack.stackTagCompound
    if (compound != null) {
      par3List.asInstanceOf[util.List[String]]
      .add(getTechnologyLevel(par1ItemStack).getTooltipEnum + getTechnology(par1ItemStack) + EnumChatFormatting.RESET)
    } else {
      par3List.asInstanceOf[util.List[String]].add("This is only valid if made via")
      par3List.asInstanceOf[util.List[String]].add("Femtocraft Research Console.")
    }
  }

  def setTechnology(itemStack: ItemStack, name: String) {
    var compound = itemStack.stackTagCompound
    if (compound == null) {
      compound = {itemStack.stackTagCompound = new NBTTagCompound; itemStack.stackTagCompound}
    }
    compound.setString(researchKey, name)
  }

  def getTechnology(itemStack: ItemStack): String = {
    val compound = itemStack.stackTagCompound
    if (compound == null) {
      return null
    }
    compound.getString(researchKey)
  }
}
