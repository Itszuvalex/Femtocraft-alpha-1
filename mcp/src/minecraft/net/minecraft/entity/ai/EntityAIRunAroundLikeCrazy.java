package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class EntityAIRunAroundLikeCrazy extends EntityAIBase
{
    private EntityHorse field_111180_a;
    private double field_111178_b;
    private double field_111179_c;
    private double field_111176_d;
    private double field_111177_e;

    public EntityAIRunAroundLikeCrazy(EntityHorse par1EntityHorse, double par2)
    {
        this.field_111180_a = par1EntityHorse;
        this.field_111178_b = par2;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.field_111180_a.func_110248_bS() && this.field_111180_a.riddenByEntity != null)
        {
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.field_111180_a, 5, 4);

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.field_111179_c = vec3.xCoord;
                this.field_111176_d = vec3.yCoord;
                this.field_111177_e = vec3.zCoord;
                return true;
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.field_111180_a.getNavigator().tryMoveToXYZ(this.field_111179_c, this.field_111176_d, this.field_111177_e, this.field_111178_b);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.field_111180_a.getNavigator().noPath() && this.field_111180_a.riddenByEntity != null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        if (this.field_111180_a.getRNG().nextInt(50) == 0)
        {
            if (this.field_111180_a.riddenByEntity instanceof EntityPlayer)
            {
                int i = this.field_111180_a.func_110252_cg();
                int j = this.field_111180_a.func_110218_cm();

                if (j > 0 && this.field_111180_a.getRNG().nextInt(j) < i)
                {
                    this.field_111180_a.func_110263_g((EntityPlayer)this.field_111180_a.riddenByEntity);
                    this.field_111180_a.worldObj.setEntityState(this.field_111180_a, (byte)7);
                    return;
                }

                this.field_111180_a.func_110198_t(5);
            }

            this.field_111180_a.riddenByEntity.mountEntity((Entity)null);
            this.field_111180_a.riddenByEntity = null;
            this.field_111180_a.func_110231_cz();
            this.field_111180_a.worldObj.setEntityState(this.field_111180_a, (byte)6);
        }
    }
}
