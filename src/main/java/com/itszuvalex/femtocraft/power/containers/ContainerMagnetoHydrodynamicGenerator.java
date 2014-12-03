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

package com.itszuvalex.femtocraft.power.containers;

import com.itszuvalex.femtocraft.core.container.ContainerBase;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/27/14.
 */
public class ContainerMagnetoHydrodynamicGenerator extends ContainerBase {
    private static final int powerIndex = 0;
    private static final int moltenSaltIndex = 1;
    private static final int contaminatedSaltIndex = 2;
    private final TileEntityMagnetohydrodynamicGenerator generator;
    private int lastPower = 0;
    private int lastMoltenSalt = 0;
    private int lastContaminatedSalt = 0;

    public ContainerMagnetoHydrodynamicGenerator(TileEntityMagnetohydrodynamicGenerator tileEntity) {
        super();
        this.generator = tileEntity;
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1iCrafting) {
        super.addCraftingToCrafters(par1iCrafting);
        sendUpdateToCrafter(this, par1iCrafting, powerIndex, generator.getCurrentPower());
        sendUpdateToCrafter(this, par1iCrafting, moltenSaltIndex, generator.getMoltenSaltTank().getFluidAmount());
        sendUpdateToCrafter(this, par1iCrafting, contaminatedSaltIndex, generator.getContaminatedSaltTank()
                .getFluidAmount());
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        int power = generator.getCurrentPower();
        int moltenSalt = generator.getMoltenSaltTank().getFluidAmount();
        int contaminatedSalt = generator.getContaminatedSaltTank().getFluidAmount();
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (lastPower != power) {
                sendUpdateToCrafter(this, icrafting, powerIndex, power);
            }
            if (lastMoltenSalt != moltenSalt) {
                sendUpdateToCrafter(this, icrafting, moltenSaltIndex, moltenSalt);
            }
            if (lastContaminatedSalt != contaminatedSalt) {
                sendUpdateToCrafter(this, icrafting, contaminatedSaltIndex, contaminatedSalt);
            }
        }
        lastPower = power;
        lastMoltenSalt = moltenSalt;
        lastContaminatedSalt = contaminatedSalt;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);
        switch (par1) {
            case powerIndex:
                generator.setCurrentStorage(par2);
                break;
            case moltenSaltIndex:
                generator.setMoltenSalt(par2);
                break;
            case contaminatedSaltIndex:
                generator.setContaminatedSalt(par2);
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return this.generator.canPlayerUse(entityplayer);
    }
}
