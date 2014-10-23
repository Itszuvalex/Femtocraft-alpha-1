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

package com.itszuvalex.femtocraft.power.plasma;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;
import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class BlockPlasma extends BlockContainer {
    public static float damagePerTick = .2f;

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4) {
        return null;
    }

    public BlockPlasma() {
        super(Material.lava);
        setCreativeTab(Femtocraft.femtocraftTab());
        setBlockUnbreakable();
        setBlockTextureName(Femtocraft.ID().toLowerCase() + ":" + "BlockPlasma");
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        return false;
    }


    @Override
    public IIcon getIcon(int par1, int par2) {
        return super.getIcon(par1, par2);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
        super.updateTick(par1World, par2, par3, par4, par5Random);
        //Tick rate is 10
        List<Entity> entities = par1World.getEntitiesWithinAABB(Entity.class,
                AxisAlignedBB.getBoundingBox
                        (par2, par3, par4, 1, 1, 1)
        );
        for (Entity entity : entities) {
            entity.attackEntityFrom(DamageSource.inFire, damagePerTick);
        }
    }

    @Override
    public int getRenderBlockPass() {
        return 1;
    }

    @Override
    public void onEntityWalking(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        super.onEntityWalking(par1World, par2, par3, par4, par5Entity);
        applyEffects(par5Entity);
    }

    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
        super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);
        applyEffects(par5Entity);
    }

    @Override
    public int getMobilityFlag() {
        return 2;
    }

    @Override
    public void onFallenUpon(World par1World, int par2, int par3, int par4, Entity par5Entity, float par6) {
        super.onFallenUpon(par1World, par2, par3, par4, par5Entity, par6);
        applyEffects(par5Entity);
    }

    @Override
    public boolean isBurning(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    private void applyEffects(Entity entity) {
        if (!entity.isBurning()) {
            entity.setFire(4);
        }
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        List<Entity> entities = par1World.getEntitiesWithinAABB(Entity.class,
                AxisAlignedBB.getBoundingBox
                        (par2, par3, par4, 1, 1, 1)
        );

        for (Entity entity : entities) {
            applyEffects(entity);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityPlasma();
    }
}
