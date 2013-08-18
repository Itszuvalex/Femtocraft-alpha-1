package net.minecraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet39AttachEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLiving extends EntityLivingBase {

   public int field_70757_a;
   public int field_70728_aV;
   private EntityLookHelper field_70749_g;
   private EntityMoveHelper field_70765_h;
   private EntityJumpHelper field_70767_i;
   private EntityBodyHelper field_70762_j;
   private PathNavigate field_70699_by;
   public final EntityAITasks field_70714_bg;
   public final EntityAITasks field_70715_bh;
   private EntityLivingBase field_70696_bz;
   private EntitySenses field_70723_bA;
   private ItemStack[] field_82182_bS = new ItemStack[5];
   protected float[] field_82174_bp = new float[5];
   private boolean field_82172_bs;
   private boolean field_82179_bU;
   protected float field_70698_bv;
   private Entity field_70776_bF;
   protected int field_70700_bx;
   private boolean field_110169_bv;
   private Entity field_110168_bw;
   private NBTTagCompound field_110170_bx;


   public EntityLiving(World p_i1595_1_) {
      super(p_i1595_1_);
      this.field_70714_bg = new EntityAITasks(p_i1595_1_ != null && p_i1595_1_.field_72984_F != null?p_i1595_1_.field_72984_F:null);
      this.field_70715_bh = new EntityAITasks(p_i1595_1_ != null && p_i1595_1_.field_72984_F != null?p_i1595_1_.field_72984_F:null);
      this.field_70749_g = new EntityLookHelper(this);
      this.field_70765_h = new EntityMoveHelper(this);
      this.field_70767_i = new EntityJumpHelper(this);
      this.field_70762_j = new EntityBodyHelper(this);
      this.field_70699_by = new PathNavigate(this, p_i1595_1_);
      this.field_70723_bA = new EntitySenses(this);

      for(int var2 = 0; var2 < this.field_82174_bp.length; ++var2) {
         this.field_82174_bp[var2] = 0.085F;
      }

   }

   protected void func_110147_ax() {
      super.func_110147_ax();
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111265_b).func_111128_a(16.0D);
   }

   public EntityLookHelper func_70671_ap() {
      return this.field_70749_g;
   }

   public EntityMoveHelper func_70605_aq() {
      return this.field_70765_h;
   }

   public EntityJumpHelper func_70683_ar() {
      return this.field_70767_i;
   }

   public PathNavigate func_70661_as() {
      return this.field_70699_by;
   }

   public EntitySenses func_70635_at() {
      return this.field_70723_bA;
   }

   public EntityLivingBase func_70638_az() {
      return this.field_70696_bz;
   }

   public void func_70624_b(EntityLivingBase p_70624_1_) {
      this.field_70696_bz = p_70624_1_;
   }

   public boolean func_70686_a(Class p_70686_1_) {
      return EntityCreeper.class != p_70686_1_ && EntityGhast.class != p_70686_1_;
   }

   public void func_70615_aA() {}

   protected void func_70088_a() {
      super.func_70088_a();
      this.field_70180_af.func_75682_a(11, Byte.valueOf((byte)0));
      this.field_70180_af.func_75682_a(10, "");
   }

   public int func_70627_aG() {
      return 80;
   }

   public void func_70642_aH() {
      String var1 = this.func_70639_aQ();
      if(var1 != null) {
         this.func_85030_a(var1, this.func_70599_aP(), this.func_70647_i());
      }

   }

   public void func_70030_z() {
      super.func_70030_z();
      this.field_70170_p.field_72984_F.func_76320_a("mobBaseTick");
      if(this.func_70089_S() && this.field_70146_Z.nextInt(1000) < this.field_70757_a++) {
         this.field_70757_a = -this.func_70627_aG();
         this.func_70642_aH();
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      if(this.field_70728_aV > 0) {
         int var2 = this.field_70728_aV;
         ItemStack[] var3 = this.func_70035_c();

         for(int var4 = 0; var4 < var3.length; ++var4) {
            if(var3[var4] != null && this.field_82174_bp[var4] <= 1.0F) {
               var2 += 1 + this.field_70146_Z.nextInt(3);
            }
         }

         return var2;
      } else {
         return this.field_70728_aV;
      }
   }

   public void func_70656_aK() {
      for(int var1 = 0; var1 < 20; ++var1) {
         double var2 = this.field_70146_Z.nextGaussian() * 0.02D;
         double var4 = this.field_70146_Z.nextGaussian() * 0.02D;
         double var6 = this.field_70146_Z.nextGaussian() * 0.02D;
         double var8 = 10.0D;
         this.field_70170_p.func_72869_a("explode", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - var2 * var8, this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O) - var4 * var8, this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N - var6 * var8, var2, var4, var6);
      }

   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if(!this.field_70170_p.field_72995_K) {
         this.func_110159_bB();
      }

   }

   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
      if(this.func_70650_aV()) {
         this.field_70762_j.func_75664_a();
         return p_110146_2_;
      } else {
         return super.func_110146_f(p_110146_1_, p_110146_2_);
      }
   }

   protected String func_70639_aQ() {
      return null;
   }

   protected int func_70633_aT() {
      return 0;
   }

   protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {
      int var3 = this.func_70633_aT();
      if(var3 > 0) {
         int var4 = this.field_70146_Z.nextInt(3);
         if(p_70628_2_ > 0) {
            var4 += this.field_70146_Z.nextInt(p_70628_2_ + 1);
         }

         for(int var5 = 0; var5 < var4; ++var5) {
            this.func_70025_b(var3, 1);
         }
      }

   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      super.func_70014_b(p_70014_1_);
      p_70014_1_.func_74757_a("CanPickUpLoot", this.func_98052_bS());
      p_70014_1_.func_74757_a("PersistenceRequired", this.field_82179_bU);
      NBTTagList var2 = new NBTTagList();

      NBTTagCompound var4;
      for(int var3 = 0; var3 < this.field_82182_bS.length; ++var3) {
         var4 = new NBTTagCompound();
         if(this.field_82182_bS[var3] != null) {
            this.field_82182_bS[var3].func_77955_b(var4);
         }

         var2.func_74742_a(var4);
      }

      p_70014_1_.func_74782_a("Equipment", var2);
      NBTTagList var6 = new NBTTagList();

      for(int var7 = 0; var7 < this.field_82174_bp.length; ++var7) {
         var6.func_74742_a(new NBTTagFloat(var7 + "", this.field_82174_bp[var7]));
      }

      p_70014_1_.func_74782_a("DropChances", var6);
      p_70014_1_.func_74778_a("CustomName", this.func_94057_bL());
      p_70014_1_.func_74757_a("CustomNameVisible", this.func_94062_bN());
      p_70014_1_.func_74757_a("Leashed", this.field_110169_bv);
      if(this.field_110168_bw != null) {
         var4 = new NBTTagCompound("Leash");
         if(this.field_110168_bw instanceof EntityLivingBase) {
            var4.func_74772_a("UUIDMost", this.field_110168_bw.func_110124_au().getMostSignificantBits());
            var4.func_74772_a("UUIDLeast", this.field_110168_bw.func_110124_au().getLeastSignificantBits());
         } else if(this.field_110168_bw instanceof EntityHanging) {
            EntityHanging var5 = (EntityHanging)this.field_110168_bw;
            var4.func_74768_a("X", var5.field_70523_b);
            var4.func_74768_a("Y", var5.field_70524_c);
            var4.func_74768_a("Z", var5.field_70521_d);
         }

         p_70014_1_.func_74782_a("Leash", var4);
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      super.func_70037_a(p_70037_1_);
      this.func_98053_h(p_70037_1_.func_74767_n("CanPickUpLoot"));
      this.field_82179_bU = p_70037_1_.func_74767_n("PersistenceRequired");
      if(p_70037_1_.func_74764_b("CustomName") && p_70037_1_.func_74779_i("CustomName").length() > 0) {
         this.func_94058_c(p_70037_1_.func_74779_i("CustomName"));
      }

      this.func_94061_f(p_70037_1_.func_74767_n("CustomNameVisible"));
      NBTTagList var2;
      int var3;
      if(p_70037_1_.func_74764_b("Equipment")) {
         var2 = p_70037_1_.func_74761_m("Equipment");

         for(var3 = 0; var3 < this.field_82182_bS.length; ++var3) {
            this.field_82182_bS[var3] = ItemStack.func_77949_a((NBTTagCompound)var2.func_74743_b(var3));
         }
      }

      if(p_70037_1_.func_74764_b("DropChances")) {
         var2 = p_70037_1_.func_74761_m("DropChances");

         for(var3 = 0; var3 < var2.func_74745_c(); ++var3) {
            this.field_82174_bp[var3] = ((NBTTagFloat)var2.func_74743_b(var3)).field_74750_a;
         }
      }

      this.field_110169_bv = p_70037_1_.func_74767_n("Leashed");
      if(this.field_110169_bv && p_70037_1_.func_74764_b("Leash")) {
         this.field_110170_bx = p_70037_1_.func_74775_l("Leash");
      }

   }

   public void func_70657_f(float p_70657_1_) {
      this.field_70701_bs = p_70657_1_;
   }

   public void func_70659_e(float p_70659_1_) {
      super.func_70659_e(p_70659_1_);
      this.func_70657_f(p_70659_1_);
   }

   public void func_70636_d() {
      super.func_70636_d();
      this.field_70170_p.field_72984_F.func_76320_a("looting");
      if(!this.field_70170_p.field_72995_K && this.func_98052_bS() && !this.field_70729_aU && this.field_70170_p.func_82736_K().func_82766_b("mobGriefing")) {
         List var1 = this.field_70170_p.func_72872_a(EntityItem.class, this.field_70121_D.func_72314_b(1.0D, 0.0D, 1.0D));
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            EntityItem var3 = (EntityItem)var2.next();
            if(!var3.field_70128_L && var3.func_92059_d() != null) {
               ItemStack var4 = var3.func_92059_d();
               int var5 = func_82159_b(var4);
               if(var5 > -1) {
                  boolean var6 = true;
                  ItemStack var7 = this.func_71124_b(var5);
                  if(var7 != null) {
                     if(var5 == 0) {
                        if(var4.func_77973_b() instanceof ItemSword && !(var7.func_77973_b() instanceof ItemSword)) {
                           var6 = true;
                        } else if(var4.func_77973_b() instanceof ItemSword && var7.func_77973_b() instanceof ItemSword) {
                           ItemSword var8 = (ItemSword)var4.func_77973_b();
                           ItemSword var9 = (ItemSword)var7.func_77973_b();
                           if(var8.func_82803_g() == var9.func_82803_g()) {
                              var6 = var4.func_77960_j() > var7.func_77960_j() || var4.func_77942_o() && !var7.func_77942_o();
                           } else {
                              var6 = var8.func_82803_g() > var9.func_82803_g();
                           }
                        } else {
                           var6 = false;
                        }
                     } else if(var4.func_77973_b() instanceof ItemArmor && !(var7.func_77973_b() instanceof ItemArmor)) {
                        var6 = true;
                     } else if(var4.func_77973_b() instanceof ItemArmor && var7.func_77973_b() instanceof ItemArmor) {
                        ItemArmor var10 = (ItemArmor)var4.func_77973_b();
                        ItemArmor var11 = (ItemArmor)var7.func_77973_b();
                        if(var10.field_77879_b == var11.field_77879_b) {
                           var6 = var4.func_77960_j() > var7.func_77960_j() || var4.func_77942_o() && !var7.func_77942_o();
                        } else {
                           var6 = var10.field_77879_b > var11.field_77879_b;
                        }
                     } else {
                        var6 = false;
                     }
                  }

                  if(var6) {
                     if(var7 != null && this.field_70146_Z.nextFloat() - 0.1F < this.field_82174_bp[var5]) {
                        this.func_70099_a(var7, 0.0F);
                     }

                     this.func_70062_b(var5, var4);
                     this.field_82174_bp[var5] = 2.0F;
                     this.field_82179_bU = true;
                     this.func_71001_a(var3, 1);
                     var3.func_70106_y();
                  }
               }
            }
         }
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected boolean func_70650_aV() {
      return false;
   }

   protected boolean func_70692_ba() {
      return true;
   }

   protected void func_70623_bb() {
      if(this.field_82179_bU) {
         this.field_70708_bq = 0;
      } else {
         EntityPlayer var1 = this.field_70170_p.func_72890_a(this, -1.0D);
         if(var1 != null) {
            double var2 = var1.field_70165_t - this.field_70165_t;
            double var4 = var1.field_70163_u - this.field_70163_u;
            double var6 = var1.field_70161_v - this.field_70161_v;
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;
            if(this.func_70692_ba() && var8 > 16384.0D) {
               this.func_70106_y();
            }

            if(this.field_70708_bq > 600 && this.field_70146_Z.nextInt(800) == 0 && var8 > 1024.0D && this.func_70692_ba()) {
               this.func_70106_y();
            } else if(var8 < 1024.0D) {
               this.field_70708_bq = 0;
            }
         }

      }
   }

   protected void func_70619_bc() {
      ++this.field_70708_bq;
      this.field_70170_p.field_72984_F.func_76320_a("checkDespawn");
      this.func_70623_bb();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("sensing");
      this.field_70723_bA.func_75523_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("targetSelector");
      this.field_70715_bh.func_75774_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("goalSelector");
      this.field_70714_bg.func_75774_a();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("navigation");
      this.field_70699_by.func_75501_e();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("mob tick");
      this.func_70629_bd();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("controls");
      this.field_70170_p.field_72984_F.func_76320_a("move");
      this.field_70765_h.func_75641_c();
      this.field_70170_p.field_72984_F.func_76318_c("look");
      this.field_70749_g.func_75649_a();
      this.field_70170_p.field_72984_F.func_76318_c("jump");
      this.field_70767_i.func_75661_b();
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_70626_be() {
      super.func_70626_be();
      this.field_70702_br = 0.0F;
      this.field_70701_bs = 0.0F;
      this.func_70623_bb();
      float var1 = 8.0F;
      if(this.field_70146_Z.nextFloat() < 0.02F) {
         EntityPlayer var2 = this.field_70170_p.func_72890_a(this, (double)var1);
         if(var2 != null) {
            this.field_70776_bF = var2;
            this.field_70700_bx = 10 + this.field_70146_Z.nextInt(20);
         } else {
            this.field_70704_bt = (this.field_70146_Z.nextFloat() - 0.5F) * 20.0F;
         }
      }

      if(this.field_70776_bF != null) {
         this.func_70625_a(this.field_70776_bF, 10.0F, (float)this.func_70646_bf());
         if(this.field_70700_bx-- <= 0 || this.field_70776_bF.field_70128_L || this.field_70776_bF.func_70068_e(this) > (double)(var1 * var1)) {
            this.field_70776_bF = null;
         }
      } else {
         if(this.field_70146_Z.nextFloat() < 0.05F) {
            this.field_70704_bt = (this.field_70146_Z.nextFloat() - 0.5F) * 20.0F;
         }

         this.field_70177_z += this.field_70704_bt;
         this.field_70125_A = this.field_70698_bv;
      }

      boolean var4 = this.func_70090_H();
      boolean var3 = this.func_70058_J();
      if(var4 || var3) {
         this.field_70703_bu = this.field_70146_Z.nextFloat() < 0.8F;
      }

   }

   public int func_70646_bf() {
      return 40;
   }

   public void func_70625_a(Entity p_70625_1_, float p_70625_2_, float p_70625_3_) {
      double var4 = p_70625_1_.field_70165_t - this.field_70165_t;
      double var8 = p_70625_1_.field_70161_v - this.field_70161_v;
      double var6;
      if(p_70625_1_ instanceof EntityLivingBase) {
         EntityLivingBase var10 = (EntityLivingBase)p_70625_1_;
         var6 = var10.field_70163_u + (double)var10.func_70047_e() - (this.field_70163_u + (double)this.func_70047_e());
      } else {
         var6 = (p_70625_1_.field_70121_D.field_72338_b + p_70625_1_.field_70121_D.field_72337_e) / 2.0D - (this.field_70163_u + (double)this.func_70047_e());
      }

      double var14 = (double)MathHelper.func_76133_a(var4 * var4 + var8 * var8);
      float var12 = (float)(Math.atan2(var8, var4) * 180.0D / 3.1415927410125732D) - 90.0F;
      float var13 = (float)(-(Math.atan2(var6, var14) * 180.0D / 3.1415927410125732D));
      this.field_70125_A = this.func_70663_b(this.field_70125_A, var13, p_70625_3_);
      this.field_70177_z = this.func_70663_b(this.field_70177_z, var12, p_70625_2_);
   }

   private float func_70663_b(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
      float var4 = MathHelper.func_76142_g(p_70663_2_ - p_70663_1_);
      if(var4 > p_70663_3_) {
         var4 = p_70663_3_;
      }

      if(var4 < -p_70663_3_) {
         var4 = -p_70663_3_;
      }

      return p_70663_1_ + var4;
   }

   public boolean func_70601_bi() {
      return this.field_70170_p.func_72855_b(this.field_70121_D) && this.field_70170_p.func_72945_a(this, this.field_70121_D).isEmpty() && !this.field_70170_p.func_72953_d(this.field_70121_D);
   }

   @SideOnly(Side.CLIENT)
   public float func_70603_bj() {
      return 1.0F;
   }

   public int func_70641_bl() {
      return 4;
   }

   public int func_82143_as() {
      if(this.func_70638_az() == null) {
         return 3;
      } else {
         int var1 = (int)(this.func_110143_aJ() - this.func_110138_aP() * 0.33F);
         var1 -= (3 - this.field_70170_p.field_73013_u) * 4;
         if(var1 < 0) {
            var1 = 0;
         }

         return var1 + 3;
      }
   }

   public ItemStack func_70694_bm() {
      return this.field_82182_bS[0];
   }

   public ItemStack func_71124_b(int p_71124_1_) {
      return this.field_82182_bS[p_71124_1_];
   }

   public ItemStack func_130225_q(int var1) {
      return this.field_82182_bS[var1 + 1];
   }

   public void func_70062_b(int p_70062_1_, ItemStack p_70062_2_) {
      this.field_82182_bS[p_70062_1_] = p_70062_2_;
   }

   public ItemStack[] func_70035_c() {
      return this.field_82182_bS;
   }

   protected void func_82160_b(boolean p_82160_1_, int p_82160_2_) {
      for(int var3 = 0; var3 < this.func_70035_c().length; ++var3) {
         ItemStack var4 = this.func_71124_b(var3);
         boolean var5 = this.field_82174_bp[var3] > 1.0F;
         if(var4 != null && (p_82160_1_ || var5) && this.field_70146_Z.nextFloat() - (float)p_82160_2_ * 0.01F < this.field_82174_bp[var3]) {
            if(!var5 && var4.func_77984_f()) {
               int var6 = Math.max(var4.func_77958_k() - 25, 1);
               int var7 = var4.func_77958_k() - this.field_70146_Z.nextInt(this.field_70146_Z.nextInt(var6) + 1);
               if(var7 > var6) {
                  var7 = var6;
               }

               if(var7 < 1) {
                  var7 = 1;
               }

               var4.func_77964_b(var7);
            }

            this.func_70099_a(var4, 0.0F);
         }
      }

   }

   protected void func_82164_bB() {
      if(this.field_70146_Z.nextFloat() < 0.15F * this.field_70170_p.func_110746_b(this.field_70165_t, this.field_70163_u, this.field_70161_v)) {
         int var1 = this.field_70146_Z.nextInt(2);
         float var2 = this.field_70170_p.field_73013_u == 3?0.1F:0.25F;
         if(this.field_70146_Z.nextFloat() < 0.095F) {
            ++var1;
         }

         if(this.field_70146_Z.nextFloat() < 0.095F) {
            ++var1;
         }

         if(this.field_70146_Z.nextFloat() < 0.095F) {
            ++var1;
         }

         for(int var3 = 3; var3 >= 0; --var3) {
            ItemStack var4 = this.func_130225_q(var3);
            if(var3 < 3 && this.field_70146_Z.nextFloat() < var2) {
               break;
            }

            if(var4 == null) {
               Item var5 = func_82161_a(var3 + 1, var1);
               if(var5 != null) {
                  this.func_70062_b(var3 + 1, new ItemStack(var5));
               }
            }
         }
      }

   }

   public static int func_82159_b(ItemStack p_82159_0_) {
      if(p_82159_0_.field_77993_c != Block.field_72061_ba.field_71990_ca && p_82159_0_.field_77993_c != Item.field_82799_bQ.field_77779_bT) {
         if(p_82159_0_.func_77973_b() instanceof ItemArmor) {
            switch(((ItemArmor)p_82159_0_.func_77973_b()).field_77881_a) {
            case 0:
               return 4;
            case 1:
               return 3;
            case 2:
               return 2;
            case 3:
               return 1;
            }
         }

         return 0;
      } else {
         return 4;
      }
   }

   public static Item func_82161_a(int p_82161_0_, int p_82161_1_) {
      switch(p_82161_0_) {
      case 4:
         if(p_82161_1_ == 0) {
            return Item.field_77687_V;
         } else if(p_82161_1_ == 1) {
            return Item.field_77796_al;
         } else if(p_82161_1_ == 2) {
            return Item.field_77694_Z;
         } else if(p_82161_1_ == 3) {
            return Item.field_77812_ad;
         } else if(p_82161_1_ == 4) {
            return Item.field_77820_ah;
         }
      case 3:
         if(p_82161_1_ == 0) {
            return Item.field_77686_W;
         } else if(p_82161_1_ == 1) {
            return Item.field_77806_am;
         } else if(p_82161_1_ == 2) {
            return Item.field_77814_aa;
         } else if(p_82161_1_ == 3) {
            return Item.field_77822_ae;
         } else if(p_82161_1_ == 4) {
            return Item.field_77798_ai;
         }
      case 2:
         if(p_82161_1_ == 0) {
            return Item.field_77693_X;
         } else if(p_82161_1_ == 1) {
            return Item.field_77808_an;
         } else if(p_82161_1_ == 2) {
            return Item.field_77816_ab;
         } else if(p_82161_1_ == 3) {
            return Item.field_77824_af;
         } else if(p_82161_1_ == 4) {
            return Item.field_77800_aj;
         }
      case 1:
         if(p_82161_1_ == 0) {
            return Item.field_77692_Y;
         } else if(p_82161_1_ == 1) {
            return Item.field_77802_ao;
         } else if(p_82161_1_ == 2) {
            return Item.field_77810_ac;
         } else if(p_82161_1_ == 3) {
            return Item.field_77818_ag;
         } else if(p_82161_1_ == 4) {
            return Item.field_77794_ak;
         }
      default:
         return null;
      }
   }

   protected void func_82162_bC() {
      float var1 = this.field_70170_p.func_110746_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      if(this.func_70694_bm() != null && this.field_70146_Z.nextFloat() < 0.25F * var1) {
         EnchantmentHelper.func_77504_a(this.field_70146_Z, this.func_70694_bm(), (int)(5.0F + var1 * (float)this.field_70146_Z.nextInt(18)));
      }

      for(int var2 = 0; var2 < 4; ++var2) {
         ItemStack var3 = this.func_130225_q(var2);
         if(var3 != null && this.field_70146_Z.nextFloat() < 0.5F * var1) {
            EnchantmentHelper.func_77504_a(this.field_70146_Z, var3, (int)(5.0F + var1 * (float)this.field_70146_Z.nextInt(18)));
         }
      }

   }

   public EntityLivingData func_110161_a(EntityLivingData p_110161_1_) {
      this.func_110148_a(SharedMonsterAttributes.field_111265_b).func_111121_a(new AttributeModifier("Random spawn bonus", this.field_70146_Z.nextGaussian() * 0.05D, 1));
      return p_110161_1_;
   }

   public boolean func_82171_bF() {
      return false;
   }

   public String func_70023_ak() {
      return this.func_94056_bM()?this.func_94057_bL():super.func_70023_ak();
   }

   public void func_110163_bv() {
      this.field_82179_bU = true;
   }

   public void func_94058_c(String p_94058_1_) {
      this.field_70180_af.func_75692_b(10, p_94058_1_);
   }

   public String func_94057_bL() {
      return this.field_70180_af.func_75681_e(10);
   }

   public boolean func_94056_bM() {
      return this.field_70180_af.func_75681_e(10).length() > 0;
   }

   public void func_94061_f(boolean p_94061_1_) {
      this.field_70180_af.func_75692_b(11, Byte.valueOf((byte)(p_94061_1_?1:0)));
   }

   public boolean func_94062_bN() {
      return this.field_70180_af.func_75683_a(11) == 1;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_94059_bO() {
      return this.func_94062_bN();
   }

   public void func_96120_a(int p_96120_1_, float p_96120_2_) {
      this.field_82174_bp[p_96120_1_] = p_96120_2_;
   }

   public boolean func_98052_bS() {
      return this.field_82172_bs;
   }

   public void func_98053_h(boolean p_98053_1_) {
      this.field_82172_bs = p_98053_1_;
   }

   public boolean func_104002_bU() {
      return this.field_82179_bU;
   }

   public final boolean func_130002_c(EntityPlayer p_130002_1_) {
      if(this.func_110167_bD() && this.func_110166_bE() == p_130002_1_) {
         this.func_110160_i(true, !p_130002_1_.field_71075_bZ.field_75098_d);
         return true;
      } else {
         ItemStack var2 = p_130002_1_.field_71071_by.func_70448_g();
         if(var2 != null && var2.field_77993_c == Item.field_111214_ch.field_77779_bT && this.func_110164_bC()) {
            if(!(this instanceof EntityTameable) || !((EntityTameable)this).func_70909_n()) {
               this.func_110162_b(p_130002_1_, true);
               --var2.field_77994_a;
               return true;
            }

            if(p_130002_1_.func_70005_c_().equalsIgnoreCase(((EntityTameable)this).func_70905_p())) {
               this.func_110162_b(p_130002_1_, true);
               --var2.field_77994_a;
               return true;
            }
         }

         return this.func_70085_c(p_130002_1_)?true:super.func_130002_c(p_130002_1_);
      }
   }

   protected boolean func_70085_c(EntityPlayer p_70085_1_) {
      return false;
   }

   protected void func_110159_bB() {
      if(this.field_110170_bx != null) {
         this.func_110165_bF();
      }

      if(this.field_110169_bv) {
         if(this.field_110168_bw == null || this.field_110168_bw.field_70128_L) {
            this.func_110160_i(true, true);
         }
      }
   }

   public void func_110160_i(boolean p_110160_1_, boolean p_110160_2_) {
      if(this.field_110169_bv) {
         this.field_110169_bv = false;
         this.field_110168_bw = null;
         if(!this.field_70170_p.field_72995_K && p_110160_2_) {
            this.func_70025_b(Item.field_111214_ch.field_77779_bT, 1);
         }

         if(!this.field_70170_p.field_72995_K && p_110160_1_ && this.field_70170_p instanceof WorldServer) {
            ((WorldServer)this.field_70170_p).func_73039_n().func_72784_a(this, new Packet39AttachEntity(1, this, (Entity)null));
         }
      }

   }

   public boolean func_110164_bC() {
      return !this.func_110167_bD() && !(this instanceof IMob);
   }

   public boolean func_110167_bD() {
      return this.field_110169_bv;
   }

   public Entity func_110166_bE() {
      return this.field_110168_bw;
   }

   public void func_110162_b(Entity p_110162_1_, boolean p_110162_2_) {
      this.field_110169_bv = true;
      this.field_110168_bw = p_110162_1_;
      if(!this.field_70170_p.field_72995_K && p_110162_2_ && this.field_70170_p instanceof WorldServer) {
         ((WorldServer)this.field_70170_p).func_73039_n().func_72784_a(this, new Packet39AttachEntity(1, this, this.field_110168_bw));
      }

   }

   private void func_110165_bF() {
      if(this.field_110169_bv && this.field_110170_bx != null) {
         if(this.field_110170_bx.func_74764_b("UUIDMost") && this.field_110170_bx.func_74764_b("UUIDLeast")) {
            UUID var5 = new UUID(this.field_110170_bx.func_74763_f("UUIDMost"), this.field_110170_bx.func_74763_f("UUIDLeast"));
            List var6 = this.field_70170_p.func_72872_a(EntityLivingBase.class, this.field_70121_D.func_72314_b(10.0D, 10.0D, 10.0D));
            Iterator var7 = var6.iterator();

            while(var7.hasNext()) {
               EntityLivingBase var8 = (EntityLivingBase)var7.next();
               if(var8.func_110124_au().equals(var5)) {
                  this.field_110168_bw = var8;
                  break;
               }
            }
         } else if(this.field_110170_bx.func_74764_b("X") && this.field_110170_bx.func_74764_b("Y") && this.field_110170_bx.func_74764_b("Z")) {
            int var1 = this.field_110170_bx.func_74762_e("X");
            int var2 = this.field_110170_bx.func_74762_e("Y");
            int var3 = this.field_110170_bx.func_74762_e("Z");
            EntityLeashKnot var4 = EntityLeashKnot.func_110130_b(this.field_70170_p, var1, var2, var3);
            if(var4 == null) {
               var4 = EntityLeashKnot.func_110129_a(this.field_70170_p, var1, var2, var3);
            }

            this.field_110168_bw = var4;
         } else {
            this.func_110160_i(false, true);
         }
      }

      this.field_110170_bx = null;
   }
}
