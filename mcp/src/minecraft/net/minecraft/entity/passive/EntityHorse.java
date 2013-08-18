package net.minecraft.entity.passive;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class EntityHorse extends EntityAnimal implements IInvBasic
{
    private static final IEntitySelector field_110276_bu = new EntityHorseBredSelector();
    private static final Attribute field_110271_bv = (new RangedAttribute("horse.jumpStrength", 0.7D, 0.0D, 2.0D)).func_111117_a("Jump Strength").func_111112_a(true);
    private static final String[] field_110270_bw = new String[] {null, "textures/entity/horse/armor/horse_armor_iron.png", "textures/entity/horse/armor/horse_armor_gold.png", "textures/entity/horse/armor/horse_armor_diamond.png"};
    private static final String[] field_110273_bx = new String[] {"", "meo", "goo", "dio"};
    private static final int[] field_110272_by = new int[] {0, 5, 7, 11};
    private static final String[] field_110268_bz = new String[] {"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
    private static final String[] field_110269_bA = new String[] {"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] field_110291_bB = new String[] {null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] field_110292_bC = new String[] {"", "wo_", "wmo", "wdo", "bdo"};
    private int field_110289_bD;
    private int field_110290_bE;
    private int field_110295_bF;
    public int field_110278_bp;
    public int field_110279_bq;
    protected boolean field_110275_br;
    private AnimalChest field_110296_bG;
    private boolean field_110293_bH;
    protected int field_110274_bs;
    protected float field_110277_bt;
    private boolean field_110294_bI;
    private float field_110283_bJ;
    private float field_110284_bK;
    private float field_110281_bL;
    private float field_110282_bM;
    private float field_110287_bN;
    private float field_110288_bO;
    private int field_110285_bP;
    private String field_110286_bQ;
    private String[] field_110280_bR = new String[3];

    public EntityHorse(World par1World)
    {
        super(par1World);
        this.setSize(1.4F, 1.6F);
        this.isImmuneToFire = false;
        this.func_110207_m(false);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.2D));
        this.tasks.addTask(1, new EntityAIRunAroundLikeCrazy(this, 1.2D));
        this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWander(this, 0.7D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.func_110226_cD();
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataWatcher.addObject(16, Integer.valueOf(0));
        this.dataWatcher.addObject(19, Byte.valueOf((byte)0));
        this.dataWatcher.addObject(20, Integer.valueOf(0));
        this.dataWatcher.addObject(21, String.valueOf(""));
        this.dataWatcher.addObject(22, Integer.valueOf(0));
    }

    public void func_110214_p(int par1)
    {
        this.dataWatcher.updateObject(19, Byte.valueOf((byte)par1));
        this.func_110230_cF();
    }

    public int func_110265_bP()
    {
        return this.dataWatcher.getWatchableObjectByte(19);
    }

    public void func_110235_q(int par1)
    {
        this.dataWatcher.updateObject(20, Integer.valueOf(par1));
        this.func_110230_cF();
    }

    public int func_110202_bQ()
    {
        return this.dataWatcher.getWatchableObjectInt(20);
    }

    /**
     * Gets the username of the entity.
     */
    public String getEntityName()
    {
        if (this.hasCustomNameTag())
        {
            return this.getCustomNameTag();
        }
        else
        {
            int i = this.func_110265_bP();

            switch (i)
            {
                case 0:
                default:
                    return StatCollector.translateToLocal("entity.horse.name");
                case 1:
                    return StatCollector.translateToLocal("entity.donkey.name");
                case 2:
                    return StatCollector.translateToLocal("entity.mule.name");
                case 3:
                    return StatCollector.translateToLocal("entity.zombiehorse.name");
                case 4:
                    return StatCollector.translateToLocal("entity.skeletonhorse.name");
            }
        }
    }

    private boolean func_110233_w(int par1)
    {
        return (this.dataWatcher.getWatchableObjectInt(16) & par1) != 0;
    }

    private void func_110208_b(int par1, boolean par2)
    {
        int j = this.dataWatcher.getWatchableObjectInt(16);

        if (par2)
        {
            this.dataWatcher.updateObject(16, Integer.valueOf(j | par1));
        }
        else
        {
            this.dataWatcher.updateObject(16, Integer.valueOf(j & ~par1));
        }
    }

    public boolean func_110228_bR()
    {
        return !this.isChild();
    }

    public boolean func_110248_bS()
    {
        return this.func_110233_w(2);
    }

    public boolean func_110253_bW()
    {
        return this.func_110228_bR();
    }

    public String func_142019_cb()
    {
        return this.dataWatcher.getWatchableObjectString(21);
    }

    public void func_110213_b(String par1Str)
    {
        this.dataWatcher.updateObject(21, par1Str);
    }

    public float func_110254_bY()
    {
        int i = this.getGrowingAge();
        return i >= 0 ? 1.0F : 0.5F + (float)(-24000 - i) / -24000.0F * 0.5F;
    }

    public void func_98054_a(boolean par1)
    {
        if (par1)
        {
            this.func_98055_j(this.func_110254_bY());
        }
        else
        {
            this.func_98055_j(1.0F);
        }
    }

    public boolean func_110246_bZ()
    {
        return this.field_110275_br;
    }

    public void func_110234_j(boolean par1)
    {
        this.func_110208_b(2, par1);
    }

    public void func_110255_k(boolean par1)
    {
        this.field_110275_br = par1;
    }

    public boolean func_110164_bC()
    {
        return !this.func_110256_cu() && super.func_110164_bC();
    }

    protected void func_142017_o(float par1)
    {
        if (par1 > 6.0F && this.func_110204_cc())
        {
            this.func_110227_p(false);
        }
    }

    public boolean func_110261_ca()
    {
        return this.func_110233_w(8);
    }

    public int func_110241_cb()
    {
        return this.dataWatcher.getWatchableObjectInt(22);
    }

    public int func_110260_d(ItemStack par1ItemStack)
    {
        return par1ItemStack == null ? 0 : (par1ItemStack.itemID == Item.field_111215_ce.itemID ? 1 : (par1ItemStack.itemID == Item.field_111216_cf.itemID ? 2 : (par1ItemStack.itemID == Item.field_111213_cg.itemID ? 3 : 0)));
    }

    public boolean func_110204_cc()
    {
        return this.func_110233_w(32);
    }

    public boolean func_110209_cd()
    {
        return this.func_110233_w(64);
    }

    public boolean func_110205_ce()
    {
        return this.func_110233_w(16);
    }

    public boolean func_110243_cf()
    {
        return this.field_110293_bH;
    }

    public void func_110236_r(int par1)
    {
        this.dataWatcher.updateObject(22, Integer.valueOf(par1));
        this.func_110230_cF();
    }

    public void func_110242_l(boolean par1)
    {
        this.func_110208_b(16, par1);
    }

    public void func_110207_m(boolean par1)
    {
        this.func_110208_b(8, par1);
    }

    public void func_110221_n(boolean par1)
    {
        this.field_110293_bH = par1;
    }

    public void func_110251_o(boolean par1)
    {
        this.func_110208_b(4, par1);
    }

    public int func_110252_cg()
    {
        return this.field_110274_bs;
    }

    public void func_110238_s(int par1)
    {
        this.field_110274_bs = par1;
    }

    public int func_110198_t(int par1)
    {
        int j = MathHelper.clamp_int(this.func_110252_cg() + par1, 0, this.func_110218_cm());
        this.func_110238_s(j);
        return j;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
    {
        Entity entity = par1DamageSource.getEntity();
        return this.riddenByEntity != null && this.riddenByEntity.equals(entity) ? false : super.attackEntityFrom(par1DamageSource, par2);
    }

    /**
     * Returns the current armor value as determined by a call to InventoryPlayer.getTotalArmorValue
     */
    public int getTotalArmorValue()
    {
        return field_110272_by[this.func_110241_cb()];
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean canBePushed()
    {
        return this.riddenByEntity == null;
    }

    public boolean func_110262_ch()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posZ);
        this.worldObj.getBiomeGenForCoords(i, j);
        return true;
    }

    public void func_110224_ci()
    {
        if (!this.worldObj.isRemote && this.func_110261_ca())
        {
            this.dropItem(Block.chest.blockID, 1);
            this.func_110207_m(false);
        }
    }

    private void func_110266_cB()
    {
        this.func_110249_cI();
        this.worldObj.playSoundAtEntity(this, "eating", 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
    }

    /**
     * Called when the mob is falling. Calculates and applies fall damage.
     */
    protected void fall(float par1)
    {
        if (par1 > 1.0F)
        {
            this.playSound("mob.horse.land", 0.4F, 1.0F);
        }

        int i = MathHelper.ceiling_float_int(par1 * 0.5F - 3.0F);

        if (i > 0)
        {
            this.attackEntityFrom(DamageSource.fall, (float)i);

            if (this.riddenByEntity != null)
            {
                this.riddenByEntity.attackEntityFrom(DamageSource.fall, (float)i);
            }

            int j = this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.2D - (double)this.prevRotationYaw), MathHelper.floor_double(this.posZ));

            if (j > 0)
            {
                StepSound stepsound = Block.blocksList[j].stepSound;
                this.worldObj.playSoundAtEntity(this, stepsound.getStepSound(), stepsound.getVolume() * 0.5F, stepsound.getPitch() * 0.75F);
            }
        }
    }

    private int func_110225_cC()
    {
        int i = this.func_110265_bP();
        return this.func_110261_ca() && (i == 1 || i == 2) ? 17 : 2;
    }

    private void func_110226_cD()
    {
        AnimalChest animalchest = this.field_110296_bG;
        this.field_110296_bG = new AnimalChest("HorseChest", this.func_110225_cC());
        this.field_110296_bG.func_110133_a(this.getEntityName());

        if (animalchest != null)
        {
            animalchest.func_110132_b(this);
            int i = Math.min(animalchest.getSizeInventory(), this.field_110296_bG.getSizeInventory());

            for (int j = 0; j < i; ++j)
            {
                ItemStack itemstack = animalchest.getStackInSlot(j);

                if (itemstack != null)
                {
                    this.field_110296_bG.setInventorySlotContents(j, itemstack.copy());
                }
            }

            animalchest = null;
        }

        this.field_110296_bG.func_110134_a(this);
        this.func_110232_cE();
    }

    private void func_110232_cE()
    {
        if (!this.worldObj.isRemote)
        {
            this.func_110251_o(this.field_110296_bG.getStackInSlot(0) != null);

            if (this.func_110259_cr())
            {
                this.func_110236_r(this.func_110260_d(this.field_110296_bG.getStackInSlot(1)));
            }
        }
    }

    /**
     * Called by InventoryBasic.onInventoryChanged() on a array that is never filled.
     */
    public void onInventoryChanged(InventoryBasic par1InventoryBasic)
    {
        int i = this.func_110241_cb();
        boolean flag = this.func_110257_ck();
        this.func_110232_cE();

        if (this.ticksExisted > 20)
        {
            if (i == 0 && i != this.func_110241_cb())
            {
                this.playSound("mob.horse.armor", 0.5F, 1.0F);
            }

            if (!flag && this.func_110257_ck())
            {
                this.playSound("mob.horse.leather", 0.5F, 1.0F);
            }
        }
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        this.func_110262_ch();
        return super.getCanSpawnHere();
    }

    protected EntityHorse func_110250_a(Entity par1Entity, double par2)
    {
        double d1 = Double.MAX_VALUE;
        Entity entity1 = null;
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(par1Entity, par1Entity.boundingBox.addCoord(par2, par2, par2), field_110276_bu);
        Iterator iterator = list.iterator();

        while (iterator.hasNext())
        {
            Entity entity2 = (Entity)iterator.next();
            double d2 = entity2.getDistanceSq(par1Entity.posX, par1Entity.posY, par1Entity.posZ);

            if (d2 < d1)
            {
                entity1 = entity2;
                d1 = d2;
            }
        }

        return (EntityHorse)entity1;
    }

    public double func_110215_cj()
    {
        return this.func_110148_a(field_110271_bv).func_111126_e();
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        this.func_110249_cI();
        int i = this.func_110265_bP();
        return i == 3 ? "mob.horse.zombie.death" : (i == 4 ? "mob.horse.skeleton.death" : (i != 1 && i != 2 ? "mob.horse.death" : "mob.horse.donkey.death"));
    }

    /**
     * Returns the item ID for the item the mob drops on death.
     */
    protected int getDropItemId()
    {
        boolean flag = this.rand.nextInt(4) == 0;
        int i = this.func_110265_bP();
        return i == 4 ? Item.bone.itemID : (i == 3 ? (flag ? 0 : Item.rottenFlesh.itemID) : Item.leather.itemID);
    }

    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        this.func_110249_cI();

        if (this.rand.nextInt(3) == 0)
        {
            this.func_110220_cK();
        }

        int i = this.func_110265_bP();
        return i == 3 ? "mob.horse.zombie.hit" : (i == 4 ? "mob.horse.skeleton.hit" : (i != 1 && i != 2 ? "mob.horse.hit" : "mob.horse.donkey.hit"));
    }

    public boolean func_110257_ck()
    {
        return this.func_110233_w(4);
    }

    /**
     * Returns the sound this mob makes while it's alive.
     */
    protected String getLivingSound()
    {
        this.func_110249_cI();

        if (this.rand.nextInt(10) == 0 && !this.isMovementBlocked())
        {
            this.func_110220_cK();
        }

        int i = this.func_110265_bP();
        return i == 3 ? "mob.horse.zombie.idle" : (i == 4 ? "mob.horse.skeleton.idle" : (i != 1 && i != 2 ? "mob.horse.idle" : "mob.horse.donkey.idle"));
    }

    protected String func_110217_cl()
    {
        this.func_110249_cI();
        this.func_110220_cK();
        int i = this.func_110265_bP();
        return i != 3 && i != 4 ? (i != 1 && i != 2 ? "mob.horse.angry" : "mob.horse.donkey.angry") : null;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    protected void playStepSound(int par1, int par2, int par3, int par4)
    {
        StepSound stepsound = Block.blocksList[par4].stepSound;

        if (this.worldObj.getBlockId(par1, par2 + 1, par3) == Block.snow.blockID)
        {
            stepsound = Block.snow.stepSound;
        }

        if (!Block.blocksList[par4].blockMaterial.isLiquid())
        {
            int i1 = this.func_110265_bP();

            if (this.riddenByEntity != null && i1 != 1 && i1 != 2)
            {
                ++this.field_110285_bP;

                if (this.field_110285_bP > 5 && this.field_110285_bP % 3 == 0)
                {
                    this.playSound("mob.horse.gallop", stepsound.getVolume() * 0.15F, stepsound.getPitch());

                    if (i1 == 0 && this.rand.nextInt(10) == 0)
                    {
                        this.playSound("mob.horse.breathe", stepsound.getVolume() * 0.6F, stepsound.getPitch());
                    }
                }
                else if (this.field_110285_bP <= 5)
                {
                    this.playSound("mob.horse.wood", stepsound.getVolume() * 0.15F, stepsound.getPitch());
                }
            }
            else if (stepsound == Block.soundWoodFootstep)
            {
                this.playSound("mob.horse.soft", stepsound.getVolume() * 0.15F, stepsound.getPitch());
            }
            else
            {
                this.playSound("mob.horse.wood", stepsound.getVolume() * 0.15F, stepsound.getPitch());
            }
        }
    }

    protected void func_110147_ax()
    {
        super.func_110147_ax();
        this.func_110140_aT().func_111150_b(field_110271_bv);
        this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(53.0D);
        this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.22499999403953552D);
    }

    /**
     * Will return how many at most can spawn in a chunk at once.
     */
    public int getMaxSpawnedInChunk()
    {
        return 6;
    }

    public int func_110218_cm()
    {
        return 100;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.8F;
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    public int getTalkInterval()
    {
        return 400;
    }

    @SideOnly(Side.CLIENT)
    public boolean func_110239_cn()
    {
        return this.func_110265_bP() == 0 || this.func_110241_cb() > 0;
    }

    private void func_110230_cF()
    {
        this.field_110286_bQ = null;
    }

    @SideOnly(Side.CLIENT)
    private void func_110247_cG()
    {
        this.field_110286_bQ = "horse/";
        this.field_110280_bR[0] = null;
        this.field_110280_bR[1] = null;
        this.field_110280_bR[2] = null;
        int i = this.func_110265_bP();
        int j = this.func_110202_bQ();
        int k;

        if (i == 0)
        {
            k = j & 255;
            int l = (j & 65280) >> 8;
            this.field_110280_bR[0] = field_110268_bz[k];
            this.field_110286_bQ = this.field_110286_bQ + field_110269_bA[k];
            this.field_110280_bR[1] = field_110291_bB[l];
            this.field_110286_bQ = this.field_110286_bQ + field_110292_bC[l];
        }
        else
        {
            this.field_110280_bR[0] = "";
            this.field_110286_bQ = this.field_110286_bQ + "_" + i + "_";
        }

        k = this.func_110241_cb();
        this.field_110280_bR[2] = field_110270_bw[k];
        this.field_110286_bQ = this.field_110286_bQ + field_110273_bx[k];
    }

    @SideOnly(Side.CLIENT)
    public String func_110264_co()
    {
        if (this.field_110286_bQ == null)
        {
            this.func_110247_cG();
        }

        return this.field_110286_bQ;
    }

    @SideOnly(Side.CLIENT)
    public String[] func_110212_cp()
    {
        if (this.field_110286_bQ == null)
        {
            this.func_110247_cG();
        }

        return this.field_110280_bR;
    }

    public void func_110199_f(EntityPlayer par1EntityPlayer)
    {
        if (!this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == par1EntityPlayer) && this.func_110248_bS())
        {
            this.field_110296_bG.func_110133_a(this.getEntityName());
            par1EntityPlayer.func_110298_a(this, this.field_110296_bG);
        }
    }

    /**
     * Called when a player interacts with a mob. e.g. gets milk from a cow, gets into the saddle on a pig.
     */
    public boolean interact(EntityPlayer par1EntityPlayer)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

        if (itemstack != null && itemstack.itemID == Item.monsterPlacer.itemID)
        {
            return super.interact(par1EntityPlayer);
        }
        else if (!this.func_110248_bS() && this.func_110256_cu())
        {
            return false;
        }
        else if (this.func_110248_bS() && this.func_110228_bR() && par1EntityPlayer.isSneaking())
        {
            this.func_110199_f(par1EntityPlayer);
            return true;
        }
        else if (this.func_110253_bW() && this.riddenByEntity != null)
        {
            return super.interact(par1EntityPlayer);
        }
        else
        {
            if (itemstack != null)
            {
                boolean flag = false;

                if (this.func_110259_cr())
                {
                    byte b0 = -1;

                    if (itemstack.itemID == Item.field_111215_ce.itemID)
                    {
                        b0 = 1;
                    }
                    else if (itemstack.itemID == Item.field_111216_cf.itemID)
                    {
                        b0 = 2;
                    }
                    else if (itemstack.itemID == Item.field_111213_cg.itemID)
                    {
                        b0 = 3;
                    }

                    if (b0 >= 0)
                    {
                        if (!this.func_110248_bS())
                        {
                            this.func_110231_cz();
                            return true;
                        }

                        this.func_110199_f(par1EntityPlayer);
                        return true;
                    }
                }

                if (!flag && !this.func_110256_cu())
                {
                    float f = 0.0F;
                    short short1 = 0;
                    byte b1 = 0;

                    if (itemstack.itemID == Item.wheat.itemID)
                    {
                        f = 2.0F;
                        short1 = 60;
                        b1 = 3;
                    }
                    else if (itemstack.itemID == Item.sugar.itemID)
                    {
                        f = 1.0F;
                        short1 = 30;
                        b1 = 3;
                    }
                    else if (itemstack.itemID == Item.bread.itemID)
                    {
                        f = 7.0F;
                        short1 = 180;
                        b1 = 3;
                    }
                    else if (itemstack.itemID == Block.field_111038_cB.blockID)
                    {
                        f = 20.0F;
                        short1 = 180;
                    }
                    else if (itemstack.itemID == Item.appleRed.itemID)
                    {
                        f = 3.0F;
                        short1 = 60;
                        b1 = 3;
                    }
                    else if (itemstack.itemID == Item.goldenCarrot.itemID)
                    {
                        f = 4.0F;
                        short1 = 60;
                        b1 = 5;

                        if (this.func_110248_bS() && this.getGrowingAge() == 0)
                        {
                            flag = true;
                            this.func_110196_bT();
                        }
                    }
                    else if (itemstack.itemID == Item.appleGold.itemID)
                    {
                        f = 10.0F;
                        short1 = 240;
                        b1 = 10;

                        if (this.func_110248_bS() && this.getGrowingAge() == 0)
                        {
                            flag = true;
                            this.func_110196_bT();
                        }
                    }

                    if (this.func_110143_aJ() < this.func_110138_aP() && f > 0.0F)
                    {
                        this.heal(f);
                        flag = true;
                    }

                    if (!this.func_110228_bR() && short1 > 0)
                    {
                        this.func_110195_a(short1);
                        flag = true;
                    }

                    if (b1 > 0 && (flag || !this.func_110248_bS()) && b1 < this.func_110218_cm())
                    {
                        flag = true;
                        this.func_110198_t(b1);
                    }

                    if (flag)
                    {
                        this.func_110266_cB();
                    }
                }

                if (!this.func_110248_bS() && !flag)
                {
                    if (itemstack != null && itemstack.func_111282_a(par1EntityPlayer, this))
                    {
                        return true;
                    }

                    this.func_110231_cz();
                    return true;
                }

                if (!flag && this.func_110229_cs() && !this.func_110261_ca() && itemstack.itemID == Block.chest.blockID)
                {
                    this.func_110207_m(true);
                    this.playSound("mob.chickenplop", 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
                    flag = true;
                    this.func_110226_cD();
                }

                if (!flag && this.func_110253_bW() && !this.func_110257_ck() && itemstack.itemID == Item.saddle.itemID)
                {
                    this.func_110199_f(par1EntityPlayer);
                    return true;
                }

                if (flag)
                {
                    if (!par1EntityPlayer.capabilities.isCreativeMode && --itemstack.stackSize == 0)
                    {
                        par1EntityPlayer.inventory.setInventorySlotContents(par1EntityPlayer.inventory.currentItem, (ItemStack)null);
                    }

                    return true;
                }
            }

            if (this.func_110253_bW() && this.riddenByEntity == null)
            {
                if (itemstack != null && itemstack.func_111282_a(par1EntityPlayer, this))
                {
                    return true;
                }
                else
                {
                    this.func_110237_h(par1EntityPlayer);
                    return true;
                }
            }
            else
            {
                return super.interact(par1EntityPlayer);
            }
        }
    }

    private void func_110237_h(EntityPlayer par1EntityPlayer)
    {
        par1EntityPlayer.rotationYaw = this.rotationYaw;
        par1EntityPlayer.rotationPitch = this.rotationPitch;
        this.func_110227_p(false);
        this.func_110219_q(false);

        if (!this.worldObj.isRemote)
        {
            par1EntityPlayer.mountEntity(this);
        }
    }

    public boolean func_110259_cr()
    {
        return this.func_110265_bP() == 0;
    }

    public boolean func_110229_cs()
    {
        int i = this.func_110265_bP();
        return i == 2 || i == 1;
    }

    /**
     * Dead and sleeping entities cannot move
     */
    protected boolean isMovementBlocked()
    {
        return this.riddenByEntity != null && this.func_110257_ck() ? true : this.func_110204_cc() || this.func_110209_cd();
    }

    public boolean func_110256_cu()
    {
        int i = this.func_110265_bP();
        return i == 3 || i == 4;
    }

    public boolean func_110222_cv()
    {
        return this.func_110256_cu() || this.func_110265_bP() == 2;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack par1ItemStack)
    {
        return false;
    }

    private void func_110210_cH()
    {
        this.field_110278_bp = 1;
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource par1DamageSource)
    {
        super.onDeath(par1DamageSource);

        if (!this.worldObj.isRemote)
        {
            this.func_110244_cA();
        }
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        if (this.rand.nextInt(200) == 0)
        {
            this.func_110210_cH();
        }

        super.onLivingUpdate();

        if (!this.worldObj.isRemote)
        {
            if (this.rand.nextInt(900) == 0 && this.deathTime == 0)
            {
                this.heal(1.0F);
            }

            if (!this.func_110204_cc() && this.riddenByEntity == null && this.rand.nextInt(300) == 0 && this.worldObj.getBlockId(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)) == Block.grass.blockID)
            {
                this.func_110227_p(true);
            }

            if (this.func_110204_cc() && ++this.field_110289_bD > 50)
            {
                this.field_110289_bD = 0;
                this.func_110227_p(false);
            }

            if (this.func_110205_ce() && !this.func_110228_bR() && !this.func_110204_cc())
            {
                EntityHorse entityhorse = this.func_110250_a(this, 16.0D);

                if (entityhorse != null && this.getDistanceSqToEntity(entityhorse) > 4.0D)
                {
                    PathEntity pathentity = this.worldObj.getPathEntityToEntity(this, entityhorse, 16.0F, true, false, false, true);
                    this.setPathToEntity(pathentity);
                }
            }
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.worldObj.isRemote && this.dataWatcher.hasChanges())
        {
            this.dataWatcher.func_111144_e();
            this.func_110230_cF();
        }

        if (this.field_110290_bE > 0 && ++this.field_110290_bE > 30)
        {
            this.field_110290_bE = 0;
            this.func_110208_b(128, false);
        }

        if (!this.worldObj.isRemote && this.field_110295_bF > 0 && ++this.field_110295_bF > 20)
        {
            this.field_110295_bF = 0;
            this.func_110219_q(false);
        }

        if (this.field_110278_bp > 0 && ++this.field_110278_bp > 8)
        {
            this.field_110278_bp = 0;
        }

        if (this.field_110279_bq > 0)
        {
            ++this.field_110279_bq;

            if (this.field_110279_bq > 300)
            {
                this.field_110279_bq = 0;
            }
        }

        this.field_110284_bK = this.field_110283_bJ;

        if (this.func_110204_cc())
        {
            this.field_110283_bJ += (1.0F - this.field_110283_bJ) * 0.4F + 0.05F;

            if (this.field_110283_bJ > 1.0F)
            {
                this.field_110283_bJ = 1.0F;
            }
        }
        else
        {
            this.field_110283_bJ += (0.0F - this.field_110283_bJ) * 0.4F - 0.05F;

            if (this.field_110283_bJ < 0.0F)
            {
                this.field_110283_bJ = 0.0F;
            }
        }

        this.field_110282_bM = this.field_110281_bL;

        if (this.func_110209_cd())
        {
            this.field_110284_bK = this.field_110283_bJ = 0.0F;
            this.field_110281_bL += (1.0F - this.field_110281_bL) * 0.4F + 0.05F;

            if (this.field_110281_bL > 1.0F)
            {
                this.field_110281_bL = 1.0F;
            }
        }
        else
        {
            this.field_110294_bI = false;
            this.field_110281_bL += (0.8F * this.field_110281_bL * this.field_110281_bL * this.field_110281_bL - this.field_110281_bL) * 0.6F - 0.05F;

            if (this.field_110281_bL < 0.0F)
            {
                this.field_110281_bL = 0.0F;
            }
        }

        this.field_110288_bO = this.field_110287_bN;

        if (this.func_110233_w(128))
        {
            this.field_110287_bN += (1.0F - this.field_110287_bN) * 0.7F + 0.05F;

            if (this.field_110287_bN > 1.0F)
            {
                this.field_110287_bN = 1.0F;
            }
        }
        else
        {
            this.field_110287_bN += (0.0F - this.field_110287_bN) * 0.7F - 0.05F;

            if (this.field_110287_bN < 0.0F)
            {
                this.field_110287_bN = 0.0F;
            }
        }
    }

    private void func_110249_cI()
    {
        if (!this.worldObj.isRemote)
        {
            this.field_110290_bE = 1;
            this.func_110208_b(128, true);
        }
    }

    private boolean func_110200_cJ()
    {
        return this.riddenByEntity == null && this.ridingEntity == null && this.func_110248_bS() && this.func_110228_bR() && !this.func_110222_cv() && this.func_110143_aJ() >= this.func_110138_aP();
    }

    public void setEating(boolean par1)
    {
        this.func_110208_b(32, par1);
    }

    public void func_110227_p(boolean par1)
    {
        this.setEating(par1);
    }

    public void func_110219_q(boolean par1)
    {
        if (par1)
        {
            this.func_110227_p(false);
        }

        this.func_110208_b(64, par1);
    }

    private void func_110220_cK()
    {
        if (!this.worldObj.isRemote)
        {
            this.field_110295_bF = 1;
            this.func_110219_q(true);
        }
    }

    public void func_110231_cz()
    {
        this.func_110220_cK();
        String s = this.func_110217_cl();

        if (s != null)
        {
            this.playSound(s, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    public void func_110244_cA()
    {
        this.func_110240_a(this, this.field_110296_bG);
        this.func_110224_ci();
    }

    private void func_110240_a(Entity par1Entity, AnimalChest par2AnimalChest)
    {
        if (par2AnimalChest != null && !this.worldObj.isRemote)
        {
            for (int i = 0; i < par2AnimalChest.getSizeInventory(); ++i)
            {
                ItemStack itemstack = par2AnimalChest.getStackInSlot(i);

                if (itemstack != null)
                {
                    this.entityDropItem(itemstack, 0.0F);
                }
            }
        }
    }

    public boolean func_110263_g(EntityPlayer par1EntityPlayer)
    {
        this.func_110213_b(par1EntityPlayer.getCommandSenderName());
        this.func_110234_j(true);
        return true;
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    public void moveEntityWithHeading(float par1, float par2)
    {
        if (this.riddenByEntity != null && this.func_110257_ck())
        {
            this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
            this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            par1 = ((EntityLivingBase)this.riddenByEntity).moveStrafing * 0.5F;
            par2 = ((EntityLivingBase)this.riddenByEntity).moveForward;

            if (par2 <= 0.0F)
            {
                par2 *= 0.25F;
                this.field_110285_bP = 0;
            }

            if (this.onGround && this.field_110277_bt == 0.0F && this.func_110209_cd() && !this.field_110294_bI)
            {
                par1 = 0.0F;
                par2 = 0.0F;
            }

            if (this.field_110277_bt > 0.0F && !this.func_110246_bZ() && this.onGround)
            {
                this.motionY = this.func_110215_cj() * (double)this.field_110277_bt;

                if (this.isPotionActive(Potion.jump))
                {
                    this.motionY += (double)((float)(this.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                }

                this.func_110255_k(true);
                this.isAirBorne = true;

                if (par2 > 0.0F)
                {
                    float f2 = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
                    float f3 = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);
                    this.motionX += (double)(-0.4F * f2 * this.field_110277_bt);
                    this.motionZ += (double)(0.4F * f3 * this.field_110277_bt);
                    this.playSound("mob.horse.jump", 0.4F, 1.0F);
                }

                this.field_110277_bt = 0.0F;
            }

            this.stepHeight = 1.0F;
            this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

            if (!this.worldObj.isRemote)
            {
                this.setAIMoveSpeed((float)this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
                super.moveEntityWithHeading(par1, par2);
            }

            if (this.onGround)
            {
                this.field_110277_bt = 0.0F;
                this.func_110255_k(false);
            }

            this.prevLimbYaw = this.limbYaw;
            double d0 = this.posX - this.prevPosX;
            double d1 = this.posZ - this.prevPosZ;
            float f4 = MathHelper.sqrt_double(d0 * d0 + d1 * d1) * 4.0F;

            if (f4 > 1.0F)
            {
                f4 = 1.0F;
            }

            this.limbYaw += (f4 - this.limbYaw) * 0.4F;
            this.limbSwing += this.limbYaw;
        }
        else
        {
            this.stepHeight = 0.5F;
            this.jumpMovementFactor = 0.02F;
            super.moveEntityWithHeading(par1, par2);
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("EatingHaystack", this.func_110204_cc());
        par1NBTTagCompound.setBoolean("ChestedHorse", this.func_110261_ca());
        par1NBTTagCompound.setBoolean("HasReproduced", this.func_110243_cf());
        par1NBTTagCompound.setBoolean("Bred", this.func_110205_ce());
        par1NBTTagCompound.setInteger("Type", this.func_110265_bP());
        par1NBTTagCompound.setInteger("Variant", this.func_110202_bQ());
        par1NBTTagCompound.setInteger("Temper", this.func_110252_cg());
        par1NBTTagCompound.setBoolean("Tame", this.func_110248_bS());
        par1NBTTagCompound.setString("OwnerName", this.func_142019_cb());

        if (this.func_110261_ca())
        {
            NBTTagList nbttaglist = new NBTTagList();

            for (int i = 2; i < this.field_110296_bG.getSizeInventory(); ++i)
            {
                ItemStack itemstack = this.field_110296_bG.getStackInSlot(i);

                if (itemstack != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte)i);
                    itemstack.writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }

            par1NBTTagCompound.setTag("Items", nbttaglist);
        }

        if (this.field_110296_bG.getStackInSlot(1) != null)
        {
            par1NBTTagCompound.setTag("ArmorItem", this.field_110296_bG.getStackInSlot(1).writeToNBT(new NBTTagCompound("ArmorItem")));
        }

        if (this.field_110296_bG.getStackInSlot(0) != null)
        {
            par1NBTTagCompound.setTag("SaddleItem", this.field_110296_bG.getStackInSlot(0).writeToNBT(new NBTTagCompound("SaddleItem")));
        }
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.func_110227_p(par1NBTTagCompound.getBoolean("EatingHaystack"));
        this.func_110242_l(par1NBTTagCompound.getBoolean("Bred"));
        this.func_110207_m(par1NBTTagCompound.getBoolean("ChestedHorse"));
        this.func_110221_n(par1NBTTagCompound.getBoolean("HasReproduced"));
        this.func_110214_p(par1NBTTagCompound.getInteger("Type"));
        this.func_110235_q(par1NBTTagCompound.getInteger("Variant"));
        this.func_110238_s(par1NBTTagCompound.getInteger("Temper"));
        this.func_110234_j(par1NBTTagCompound.getBoolean("Tame"));

        if (par1NBTTagCompound.hasKey("OwnerName"))
        {
            this.func_110213_b(par1NBTTagCompound.getString("OwnerName"));
        }

        AttributeInstance attributeinstance = this.func_110140_aT().func_111152_a("Speed");

        if (attributeinstance != null)
        {
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(attributeinstance.func_111125_b() * 0.25D);
        }

        if (this.func_110261_ca())
        {
            NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items");
            this.func_110226_cD();

            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
                int j = nbttagcompound1.getByte("Slot") & 255;

                if (j >= 2 && j < this.field_110296_bG.getSizeInventory())
                {
                    this.field_110296_bG.setInventorySlotContents(j, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }

        ItemStack itemstack;

        if (par1NBTTagCompound.hasKey("ArmorItem"))
        {
            itemstack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("ArmorItem"));

            if (itemstack != null && func_110211_v(itemstack.itemID))
            {
                this.field_110296_bG.setInventorySlotContents(1, itemstack);
            }
        }

        if (par1NBTTagCompound.hasKey("SaddleItem"))
        {
            itemstack = ItemStack.loadItemStackFromNBT(par1NBTTagCompound.getCompoundTag("SaddleItem"));

            if (itemstack != null && itemstack.itemID == Item.saddle.itemID)
            {
                this.field_110296_bG.setInventorySlotContents(0, itemstack);
            }
        }
        else if (par1NBTTagCompound.getBoolean("Saddle"))
        {
            this.field_110296_bG.setInventorySlotContents(0, new ItemStack(Item.saddle));
        }

        this.func_110232_cE();
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal par1EntityAnimal)
    {
        if (par1EntityAnimal == this)
        {
            return false;
        }
        else if (par1EntityAnimal.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            EntityHorse entityhorse = (EntityHorse)par1EntityAnimal;

            if (this.func_110200_cJ() && entityhorse.func_110200_cJ())
            {
                int i = this.func_110265_bP();
                int j = entityhorse.func_110265_bP();
                return i == j || i == 0 && j == 1 || i == 1 && j == 0;
            }
            else
            {
                return false;
            }
        }
    }

    public EntityAgeable createChild(EntityAgeable par1EntityAgeable)
    {
        EntityHorse entityhorse = (EntityHorse)par1EntityAgeable;
        EntityHorse entityhorse1 = new EntityHorse(this.worldObj);
        int i = this.func_110265_bP();
        int j = entityhorse.func_110265_bP();
        int k = 0;

        if (i == j)
        {
            k = i;
        }
        else if (i == 0 && j == 1 || i == 1 && j == 0)
        {
            k = 2;
        }

        if (k == 0)
        {
            int l = this.rand.nextInt(9);
            int i1;

            if (l < 4)
            {
                i1 = this.func_110202_bQ() & 255;
            }
            else if (l < 8)
            {
                i1 = entityhorse.func_110202_bQ() & 255;
            }
            else
            {
                i1 = this.rand.nextInt(7);
            }

            int j1 = this.rand.nextInt(5);

            if (j1 < 4)
            {
                i1 |= this.func_110202_bQ() & 65280;
            }
            else if (j1 < 8)
            {
                i1 |= entityhorse.func_110202_bQ() & 65280;
            }
            else
            {
                i1 |= this.rand.nextInt(5) << 8 & 65280;
            }

            entityhorse1.func_110235_q(i1);
        }

        entityhorse1.func_110214_p(k);
        double d0 = this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() + par1EntityAgeable.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111125_b() + (double)this.func_110267_cL();
        entityhorse1.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(d0 / 3.0D);
        double d1 = this.func_110148_a(field_110271_bv).func_111125_b() + par1EntityAgeable.func_110148_a(field_110271_bv).func_111125_b() + this.func_110245_cM();
        entityhorse1.func_110148_a(field_110271_bv).func_111128_a(d1 / 3.0D);
        double d2 = this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() + par1EntityAgeable.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111125_b() + this.func_110203_cN();
        entityhorse1.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(d2 / 3.0D);
        return entityhorse1;
    }

    public EntityLivingData func_110161_a(EntityLivingData par1EntityLivingData)
    {
        Object par1EntityLivingData1 = super.func_110161_a(par1EntityLivingData);
        boolean flag = false;
        int i = 0;
        int j;

        if (par1EntityLivingData1 instanceof EntityHorseGroupData)
        {
            j = ((EntityHorseGroupData)par1EntityLivingData1).field_111107_a;
            i = ((EntityHorseGroupData)par1EntityLivingData1).field_111106_b & 255 | this.rand.nextInt(5) << 8;
        }
        else
        {
            if (this.rand.nextInt(10) == 0)
            {
                j = 1;
            }
            else
            {
                int k = this.rand.nextInt(7);
                int l = this.rand.nextInt(5);
                j = 0;
                i = k | l << 8;
            }

            par1EntityLivingData1 = new EntityHorseGroupData(j, i);
        }

        this.func_110214_p(j);
        this.func_110235_q(i);

        if (this.rand.nextInt(5) == 0)
        {
            this.setGrowingAge(-24000);
        }

        if (j != 4 && j != 3)
        {
            this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a((double)this.func_110267_cL());

            if (j == 0)
            {
                this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(this.func_110203_cN());
            }
            else
            {
                this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.17499999701976776D);
            }
        }
        else
        {
            this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(15.0D);
            this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.20000000298023224D);
        }

        if (j != 2 && j != 1)
        {
            this.func_110148_a(field_110271_bv).func_111128_a(this.func_110245_cM());
        }
        else
        {
            this.func_110148_a(field_110271_bv).func_111128_a(0.5D);
        }

        this.setEntityHealth(this.func_110138_aP());
        return (EntityLivingData)par1EntityLivingData1;
    }

    @SideOnly(Side.CLIENT)
    public float func_110258_o(float par1)
    {
        return this.field_110284_bK + (this.field_110283_bJ - this.field_110284_bK) * par1;
    }

    @SideOnly(Side.CLIENT)
    public float func_110223_p(float par1)
    {
        return this.field_110282_bM + (this.field_110281_bL - this.field_110282_bM) * par1;
    }

    @SideOnly(Side.CLIENT)
    public float func_110201_q(float par1)
    {
        return this.field_110288_bO + (this.field_110287_bN - this.field_110288_bO) * par1;
    }

    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }

    public void func_110206_u(int par1)
    {
        if (this.func_110257_ck())
        {
            if (par1 < 0)
            {
                par1 = 0;
            }
            else
            {
                this.field_110294_bI = true;
                this.func_110220_cK();
            }

            if (par1 >= 90)
            {
                this.field_110277_bt = 1.0F;
            }
            else
            {
                this.field_110277_bt = 0.4F + 0.4F * (float)par1 / 90.0F;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void func_110216_r(boolean par1)
    {
        String s = par1 ? "heart" : "smoke";

        for (int i = 0; i < 7; ++i)
        {
            double d0 = this.rand.nextGaussian() * 0.02D;
            double d1 = this.rand.nextGaussian() * 0.02D;
            double d2 = this.rand.nextGaussian() * 0.02D;
            this.worldObj.spawnParticle(s, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 0.5D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2);
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 7)
        {
            this.func_110216_r(true);
        }
        else if (par1 == 6)
        {
            this.func_110216_r(false);
        }
        else
        {
            super.handleHealthUpdate(par1);
        }
    }

    public void updateRiderPosition()
    {
        super.updateRiderPosition();

        if (this.field_110282_bM > 0.0F)
        {
            float f = MathHelper.sin(this.renderYawOffset * (float)Math.PI / 180.0F);
            float f1 = MathHelper.cos(this.renderYawOffset * (float)Math.PI / 180.0F);
            float f2 = 0.7F * this.field_110282_bM;
            float f3 = 0.15F * this.field_110282_bM;
            this.riddenByEntity.setPosition(this.posX + (double)(f2 * f), this.posY + this.getMountedYOffset() + this.riddenByEntity.getYOffset() + (double)f3, this.posZ - (double)(f2 * f1));

            if (this.riddenByEntity instanceof EntityLivingBase)
            {
                ((EntityLivingBase)this.riddenByEntity).renderYawOffset = this.renderYawOffset;
            }
        }
    }

    private float func_110267_cL()
    {
        return 15.0F + (float)this.rand.nextInt(8) + (float)this.rand.nextInt(9);
    }

    private double func_110245_cM()
    {
        return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
    }

    private double func_110203_cN()
    {
        return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
    }

    public static boolean func_110211_v(int par0)
    {
        return par0 == Item.field_111215_ce.itemID || par0 == Item.field_111216_cf.itemID || par0 == Item.field_111213_cg.itemID;
    }

    /**
     * returns true if this entity is by a ladder, false otherwise
     */
    public boolean isOnLadder()
    {
        return false;
    }
}
