package net.minecraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging
{
    public EntityLeashKnot(World par1World)
    {
        super(par1World);
    }

    public EntityLeashKnot(World par1World, int par2, int par3, int par4)
    {
        super(par1World, par2, par3, par4, 0);
        this.setPosition((double)par2 + 0.5D, (double)par3 + 0.5D, (double)par4 + 0.5D);
    }

    protected void entityInit()
    {
        super.entityInit();
    }

    public void setDirection(int par1) {}

    public int func_82329_d()
    {
        return 9;
    }

    public int func_82330_g()
    {
        return 9;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    public boolean isInRangeToRenderDist(double par1)
    {
        return par1 < 1024.0D;
    }

    public void func_110128_b(Entity par1Entity) {}

    /**
     * adds the ID of this entity to the NBT given
     */
    public boolean addEntityID(NBTTagCompound par1NBTTagCompound)
    {
        return false;
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

    public boolean func_130002_c(EntityPlayer par1EntityPlayer)
    {
        ItemStack itemstack = par1EntityPlayer.getHeldItem();
        boolean flag = false;
        double d0;
        List list;
        Iterator iterator;
        EntityLiving entityliving;

        if (itemstack != null && itemstack.itemID == Item.field_111214_ch.itemID && !this.worldObj.isRemote)
        {
            d0 = 7.0D;
            list = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + d0, this.posZ + d0));

            if (list != null)
            {
                iterator = list.iterator();

                while (iterator.hasNext())
                {
                    entityliving = (EntityLiving)iterator.next();

                    if (entityliving.func_110167_bD() && entityliving.func_110166_bE() == par1EntityPlayer)
                    {
                        entityliving.func_110162_b(this, true);
                        flag = true;
                    }
                }
            }
        }

        if (!this.worldObj.isRemote && !flag)
        {
            this.setDead();

            if (par1EntityPlayer.capabilities.isCreativeMode)
            {
                d0 = 7.0D;
                list = this.worldObj.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.getAABBPool().getAABB(this.posX - d0, this.posY - d0, this.posZ - d0, this.posX + d0, this.posY + d0, this.posZ + d0));

                if (list != null)
                {
                    iterator = list.iterator();

                    while (iterator.hasNext())
                    {
                        entityliving = (EntityLiving)iterator.next();

                        if (entityliving.func_110167_bD() && entityliving.func_110166_bE() == this)
                        {
                            entityliving.func_110160_i(true, false);
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * checks to make sure painting can be placed there
     */
    public boolean onValidSurface()
    {
        int i = this.worldObj.getBlockId(this.xPosition, this.yPosition, this.zPosition);
        return Block.blocksList[i] != null && Block.blocksList[i].getRenderType() == 11;
    }

    public static EntityLeashKnot func_110129_a(World par0World, int par1, int par2, int par3)
    {
        EntityLeashKnot entityleashknot = new EntityLeashKnot(par0World, par1, par2, par3);
        entityleashknot.field_98038_p = true;
        par0World.spawnEntityInWorld(entityleashknot);
        return entityleashknot;
    }

    public static EntityLeashKnot func_110130_b(World par0World, int par1, int par2, int par3)
    {
        List list = par0World.getEntitiesWithinAABB(EntityLeashKnot.class, AxisAlignedBB.getAABBPool().getAABB((double)par1 - 1.0D, (double)par2 - 1.0D, (double)par3 - 1.0D, (double)par1 + 1.0D, (double)par2 + 1.0D, (double)par3 + 1.0D));
        Object object = null;

        if (list != null)
        {
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityLeashKnot entityleashknot = (EntityLeashKnot)iterator.next();

                if (entityleashknot.xPosition == par1 && entityleashknot.yPosition == par2 && entityleashknot.zPosition == par3)
                {
                    return entityleashknot;
                }
            }
        }

        return null;
    }
}
