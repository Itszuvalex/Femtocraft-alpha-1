/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.core.items.CoreItemBlock;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPowerBase;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.util.List;

public abstract class ItemBlockPower extends CoreItemBlock {

    public ItemBlockPower(int par1) {
        super(par1);
    }

    // @Override
    // public boolean placeBlockAt(ItemStack stack, EntityPlayer player,
    // World world, int x, int y, int z, int side, float hitX, float hitY,
    // float hitZ, int metadata) {
    // // TODO Auto-generated method stub
    // return super.placeBlockAt(stack, player, world, x, y, z, side, hitX,
    // hitY,
    // hitZ, metadata);
    // }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack,
                               EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

        NBTTagCompound nbt = par1ItemStack.getTagCompound();
        if (nbt == null) {
            nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
        }

        String fieldName = getFieldName();
        PowerContainer container;
        boolean init = nbt.hasKey(fieldName);

        NBTTagCompound power = nbt.getCompoundTag(fieldName);

        if (!init) {
            container = getDefaultContainer();
            container.saveToNBT(power);
            nbt.setTag(fieldName, power);
        }
        else {
            container = PowerContainer.createFromNBT(power);
        }

        container.addInformationToTooltip(par3List);
    }

    private String getFieldName() {
        Field[] fields = TileEntityPowerBase.class.getDeclaredFields();
        String fieldName = "power";
        for (Field field : fields) {
            if (field.getType() == PowerContainer.class) {
                String ret;
                boolean access = field.isAccessible();
                if (!access) {
                    field.setAccessible(true);
                }
                ret = field.getName();
                if (!access) {
                    field.setAccessible(false);
                }
                return ret;
            }
        }
        return fieldName;
    }

    public abstract PowerContainer getDefaultContainer();

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World,
                          EntityPlayer par3EntityPlayer) {
        super.onCreated(par1ItemStack, par2World, par3EntityPlayer);

        NBTTagCompound nbt = par1ItemStack.getTagCompound();
        if (nbt == null) {
            nbt = par1ItemStack.stackTagCompound = new NBTTagCompound();
        }
        NBTTagCompound power = new NBTTagCompound();
        getDefaultContainer().saveToNBT(power);
        nbt.setTag(getFieldName(), power);
    }
}
