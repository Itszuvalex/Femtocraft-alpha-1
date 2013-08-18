package net.minecraft.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.StepSound;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeInstance;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.network.packet.Packet18Animation;
import net.minecraft.network.packet.Packet22Collect;
import net.minecraft.network.packet.Packet5PlayerInventory;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class EntityLivingBase extends Entity {

   private static final UUID field_110156_b = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final AttributeModifier field_110157_c = (new AttributeModifier(field_110156_b, "Sprinting speed boost", 0.30000001192092896D, 2)).func_111168_a(false);
   private BaseAttributeMap field_110155_d;
   private final CombatTracker field_94063_bt = new CombatTracker(this);
   private final HashMap field_70713_bf = new HashMap();
   private final ItemStack[] field_82180_bT = new ItemStack[5];
   public boolean field_82175_bq;
   public int field_110158_av;
   public int field_70720_be;
   public float field_70735_aL;
   public int field_70737_aN;
   public int field_70738_aO;
   public float field_70739_aP;
   public int field_70725_aQ;
   public int field_70724_aR;
   public float field_70732_aI;
   public float field_70733_aJ;
   public float field_70722_aY;
   public float field_70721_aZ;
   public float field_70754_ba;
   public int field_70771_an = 20;
   public float field_70727_aS;
   public float field_70726_aT;
   public float field_70769_ao;
   public float field_70770_ap;
   public float field_70761_aq;
   public float field_70760_ar;
   public float field_70759_as;
   public float field_70758_at;
   public float field_70747_aH = 0.02F;
   protected EntityPlayer field_70717_bb;
   protected int field_70718_bc;
   protected boolean field_70729_aU;
   protected int field_70708_bq;
   protected float field_70768_au;
   protected float field_110154_aX;
   protected float field_70764_aw;
   protected float field_70763_ax;
   protected float field_70741_aB;
   protected int field_70744_aE;
   protected float field_110153_bc;
   protected boolean field_70703_bu;
   public float field_70702_br;
   public float field_70701_bs;
   protected float field_70704_bt;
   protected int field_70716_bi;
   protected double field_70709_bj;
   protected double field_70710_bk;
   protected double field_110152_bk;
   protected double field_70712_bm;
   protected double field_70705_bn;
   private boolean field_70752_e = true;
   private EntityLivingBase field_70755_b;
   private int field_70756_c;
   private EntityLivingBase field_110150_bn;
   private int field_142016_bo;
   private float field_70746_aG;
   private int field_70773_bE;
   private float field_110151_bq;


   public EntityLivingBase(World p_i1594_1_) {
      super(p_i1594_1_);
      this.func_110147_ax();
      this.func_70606_j(this.func_110138_aP());
      this.field_70156_m = true;
      this.field_70770_ap = (float)(Math.random() + 1.0D) * 0.01F;
      this.func_70107_b(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      this.field_70769_ao = (float)Math.random() * 12398.0F;
      this.field_70177_z = (float)(Math.random() * 3.1415927410125732D * 2.0D);
      this.field_70759_as = this.field_70177_z;
      this.field_70138_W = 0.5F;
   }

   protected void func_70088_a() {
      this.field_70180_af.func_75682_a(7, Integer.valueOf(0));
      this.field_70180_af.func_75682_a(8, Byte.valueOf((byte)0));
      this.field_70180_af.func_75682_a(9, Byte.valueOf((byte)0));
      this.field_70180_af.func_75682_a(6, Float.valueOf(1.0F));
   }

   protected void func_110147_ax() {
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111267_a);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111266_c);
      this.func_110140_aT().func_111150_b(SharedMonsterAttributes.field_111263_d);
      if(!this.func_70650_aV()) {
         this.func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.10000000149011612D);
      }

   }

   protected void func_70064_a(double p_70064_1_, boolean p_70064_3_) {
      if(!this.func_70090_H()) {
         this.func_70072_I();
      }

      if(p_70064_3_ && this.field_70143_R > 0.0F) {
         int var4 = MathHelper.func_76128_c(this.field_70165_t);
         int var5 = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D - (double)this.field_70129_M);
         int var6 = MathHelper.func_76128_c(this.field_70161_v);
         int var7 = this.field_70170_p.func_72798_a(var4, var5, var6);
         if(var7 == 0) {
            int var8 = this.field_70170_p.func_85175_e(var4, var5 - 1, var6);
            if(var8 == 11 || var8 == 32 || var8 == 21) {
               var7 = this.field_70170_p.func_72798_a(var4, var5 - 1, var6);
            }
         }

         if(var7 > 0) {
            Block.field_71973_m[var7].func_71866_a(this.field_70170_p, var4, var5, var6, this, this.field_70143_R);
         }
      }

      super.func_70064_a(p_70064_1_, p_70064_3_);
   }

   public boolean func_70648_aU() {
      return false;
   }

   public void func_70030_z() {
      this.field_70732_aI = this.field_70733_aJ;
      super.func_70030_z();
      this.field_70170_p.field_72984_F.func_76320_a("livingEntityBaseTick");
      if(this.func_70089_S() && this.func_70094_T()) {
         this.func_70097_a(DamageSource.field_76368_d, 1.0F);
      }

      if(this.func_70045_F() || this.field_70170_p.field_72995_K) {
         this.func_70066_B();
      }

      boolean var1 = this instanceof EntityPlayer && ((EntityPlayer)this).field_71075_bZ.field_75102_a;
      if(this.func_70089_S() && this.func_70055_a(Material.field_76244_g)) {
         if(!this.func_70648_aU() && !this.func_82165_m(Potion.field_76427_o.field_76415_H) && !var1) {
            this.func_70050_g(this.func_70682_h(this.func_70086_ai()));
            if(this.func_70086_ai() == -20) {
               this.func_70050_g(0);

               for(int var2 = 0; var2 < 8; ++var2) {
                  float var3 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                  float var4 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                  float var5 = this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat();
                  this.field_70170_p.func_72869_a("bubble", this.field_70165_t + (double)var3, this.field_70163_u + (double)var4, this.field_70161_v + (double)var5, this.field_70159_w, this.field_70181_x, this.field_70179_y);
               }

               this.func_70097_a(DamageSource.field_76369_e, 2.0F);
            }
         }

         this.func_70066_B();
         if(!this.field_70170_p.field_72995_K && this.func_70115_ae() && this.field_70154_o instanceof EntityLivingBase) {
            this.func_70078_a((Entity)null);
         }
      } else {
         this.func_70050_g(300);
      }

      this.field_70727_aS = this.field_70726_aT;
      if(this.field_70724_aR > 0) {
         --this.field_70724_aR;
      }

      if(this.field_70737_aN > 0) {
         --this.field_70737_aN;
      }

      if(this.field_70172_ad > 0) {
         --this.field_70172_ad;
      }

      if(this.func_110143_aJ() <= 0.0F) {
         this.func_70609_aI();
      }

      if(this.field_70718_bc > 0) {
         --this.field_70718_bc;
      } else {
         this.field_70717_bb = null;
      }

      if(this.field_110150_bn != null && !this.field_110150_bn.func_70089_S()) {
         this.field_110150_bn = null;
      }

      if(this.field_70755_b != null && !this.field_70755_b.func_70089_S()) {
         this.func_70604_c((EntityLivingBase)null);
      }

      this.func_70679_bo();
      this.field_70763_ax = this.field_70764_aw;
      this.field_70760_ar = this.field_70761_aq;
      this.field_70758_at = this.field_70759_as;
      this.field_70126_B = this.field_70177_z;
      this.field_70127_C = this.field_70125_A;
      this.field_70170_p.field_72984_F.func_76319_b();
   }

   public boolean func_70631_g_() {
      return false;
   }

   protected void func_70609_aI() {
      ++this.field_70725_aQ;
      if(this.field_70725_aQ == 20) {
         int var1;
         if(!this.field_70170_p.field_72995_K && (this.field_70718_bc > 0 || this.func_70684_aJ()) && !this.func_70631_g_() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
            var1 = this.func_70693_a(this.field_70717_bb);

            while(var1 > 0) {
               int var2 = EntityXPOrb.func_70527_a(var1);
               var1 -= var2;
               this.field_70170_p.func_72838_d(new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, var2));
            }
         }

         this.func_70106_y();

         for(var1 = 0; var1 < 20; ++var1) {
            double var8 = this.field_70146_Z.nextGaussian() * 0.02D;
            double var4 = this.field_70146_Z.nextGaussian() * 0.02D;
            double var6 = this.field_70146_Z.nextGaussian() * 0.02D;
            this.field_70170_p.func_72869_a("explode", this.field_70165_t + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, this.field_70163_u + (double)(this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (double)(this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - (double)this.field_70130_N, var8, var4, var6);
         }
      }

   }

   protected int func_70682_h(int p_70682_1_) {
      int var2 = EnchantmentHelper.func_77501_a(this);
      return var2 > 0 && this.field_70146_Z.nextInt(var2 + 1) > 0?p_70682_1_:p_70682_1_ - 1;
   }

   protected int func_70693_a(EntityPlayer p_70693_1_) {
      return 0;
   }

   protected boolean func_70684_aJ() {
      return false;
   }

   public Random func_70681_au() {
      return this.field_70146_Z;
   }

   public EntityLivingBase func_70643_av() {
      return this.field_70755_b;
   }

   public int func_142015_aE() {
      return this.field_70756_c;
   }

   public void func_70604_c(EntityLivingBase p_70604_1_) {
      this.field_70755_b = p_70604_1_;
      this.field_70756_c = this.field_70173_aa;
   }

   public EntityLivingBase func_110144_aD() {
      return this.field_110150_bn;
   }

   public int func_142013_aG() {
      return this.field_142016_bo;
   }

   public void func_130011_c(Entity p_130011_1_) {
      if(p_130011_1_ instanceof EntityLivingBase) {
         this.field_110150_bn = (EntityLivingBase)p_130011_1_;
      } else {
         this.field_110150_bn = null;
      }

      this.field_142016_bo = this.field_70173_aa;
   }

   public int func_70654_ax() {
      return this.field_70708_bq;
   }

   public void func_70014_b(NBTTagCompound p_70014_1_) {
      p_70014_1_.func_74776_a("HealF", this.func_110143_aJ());
      p_70014_1_.func_74777_a("Health", (short)((int)Math.ceil((double)this.func_110143_aJ())));
      p_70014_1_.func_74777_a("HurtTime", (short)this.field_70737_aN);
      p_70014_1_.func_74777_a("DeathTime", (short)this.field_70725_aQ);
      p_70014_1_.func_74777_a("AttackTime", (short)this.field_70724_aR);
      p_70014_1_.func_74776_a("AbsorptionAmount", this.func_110139_bj());
      ItemStack[] var2 = this.func_70035_c();
      int var3 = var2.length;

      int var4;
      ItemStack var5;
      for(var4 = 0; var4 < var3; ++var4) {
         var5 = var2[var4];
         if(var5 != null) {
            this.field_110155_d.func_111148_a(var5.func_111283_C());
         }
      }

      p_70014_1_.func_74782_a("Attributes", SharedMonsterAttributes.func_111257_a(this.func_110140_aT()));
      var2 = this.func_70035_c();
      var3 = var2.length;

      for(var4 = 0; var4 < var3; ++var4) {
         var5 = var2[var4];
         if(var5 != null) {
            this.field_110155_d.func_111147_b(var5.func_111283_C());
         }
      }

      if(!this.field_70713_bf.isEmpty()) {
         NBTTagList var6 = new NBTTagList();
         Iterator var7 = this.field_70713_bf.values().iterator();

         while(var7.hasNext()) {
            PotionEffect var8 = (PotionEffect)var7.next();
            var6.func_74742_a(var8.func_82719_a(new NBTTagCompound()));
         }

         p_70014_1_.func_74782_a("ActiveEffects", var6);
      }

   }

   public void func_70037_a(NBTTagCompound p_70037_1_) {
      this.func_110149_m(p_70037_1_.func_74760_g("AbsorptionAmount"));
      if(p_70037_1_.func_74764_b("Attributes") && this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
         SharedMonsterAttributes.func_111260_a(this.func_110140_aT(), p_70037_1_.func_74761_m("Attributes"), this.field_70170_p == null?null:this.field_70170_p.func_98180_V());
      }

      if(p_70037_1_.func_74764_b("ActiveEffects")) {
         NBTTagList var2 = p_70037_1_.func_74761_m("ActiveEffects");

         for(int var3 = 0; var3 < var2.func_74745_c(); ++var3) {
            NBTTagCompound var4 = (NBTTagCompound)var2.func_74743_b(var3);
            PotionEffect var5 = PotionEffect.func_82722_b(var4);
            this.field_70713_bf.put(Integer.valueOf(var5.func_76456_a()), var5);
         }
      }

      if(p_70037_1_.func_74764_b("HealF")) {
         this.func_70606_j(p_70037_1_.func_74760_g("HealF"));
      } else {
         NBTBase var6 = p_70037_1_.func_74781_a("Health");
         if(var6 == null) {
            this.func_70606_j(this.func_110138_aP());
         } else if(var6.func_74732_a() == 5) {
            this.func_70606_j(((NBTTagFloat)var6).field_74750_a);
         } else if(var6.func_74732_a() == 2) {
            this.func_70606_j((float)((NBTTagShort)var6).field_74752_a);
         }
      }

      this.field_70737_aN = p_70037_1_.func_74765_d("HurtTime");
      this.field_70725_aQ = p_70037_1_.func_74765_d("DeathTime");
      this.field_70724_aR = p_70037_1_.func_74765_d("AttackTime");
   }

   protected void func_70679_bo() {
      Iterator var1 = this.field_70713_bf.keySet().iterator();

      while(var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = (PotionEffect)this.field_70713_bf.get(var2);
         if(!var3.func_76455_a(this)) {
            if(!this.field_70170_p.field_72995_K) {
               var1.remove();
               this.func_70688_c(var3);
            }
         } else if(var3.func_76459_b() % 600 == 0) {
            this.func_70695_b(var3, false);
         }
      }

      int var11;
      if(this.field_70752_e) {
         if(!this.field_70170_p.field_72995_K) {
            if(this.field_70713_bf.isEmpty()) {
               this.field_70180_af.func_75692_b(8, Byte.valueOf((byte)0));
               this.field_70180_af.func_75692_b(7, Integer.valueOf(0));
               this.func_82142_c(false);
            } else {
               var11 = PotionHelper.func_77911_a(this.field_70713_bf.values());
               this.field_70180_af.func_75692_b(8, Byte.valueOf((byte)(PotionHelper.func_82817_b(this.field_70713_bf.values())?1:0)));
               this.field_70180_af.func_75692_b(7, Integer.valueOf(var11));
               this.func_82142_c(this.func_82165_m(Potion.field_76441_p.field_76415_H));
            }
         }

         this.field_70752_e = false;
      }

      var11 = this.field_70180_af.func_75679_c(7);
      boolean var12 = this.field_70180_af.func_75683_a(8) > 0;
      if(var11 > 0) {
         boolean var4 = false;
         if(!this.func_82150_aj()) {
            var4 = this.field_70146_Z.nextBoolean();
         } else {
            var4 = this.field_70146_Z.nextInt(15) == 0;
         }

         if(var12) {
            var4 &= this.field_70146_Z.nextInt(5) == 0;
         }

         if(var4 && var11 > 0) {
            double var5 = (double)(var11 >> 16 & 255) / 255.0D;
            double var7 = (double)(var11 >> 8 & 255) / 255.0D;
            double var9 = (double)(var11 >> 0 & 255) / 255.0D;
            this.field_70170_p.func_72869_a(var12?"mobSpellAmbient":"mobSpell", this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, this.field_70163_u + this.field_70146_Z.nextDouble() * (double)this.field_70131_O - (double)this.field_70129_M, this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * (double)this.field_70130_N, var5, var7, var9);
         }
      }

   }

   public void func_70674_bp() {
      Iterator var1 = this.field_70713_bf.keySet().iterator();

      while(var1.hasNext()) {
         Integer var2 = (Integer)var1.next();
         PotionEffect var3 = (PotionEffect)this.field_70713_bf.get(var2);
         if(!this.field_70170_p.field_72995_K) {
            var1.remove();
            this.func_70688_c(var3);
         }
      }

   }

   public Collection func_70651_bq() {
      return this.field_70713_bf.values();
   }

   public boolean func_82165_m(int p_82165_1_) {
      return this.field_70713_bf.containsKey(Integer.valueOf(p_82165_1_));
   }

   public boolean func_70644_a(Potion p_70644_1_) {
      return this.field_70713_bf.containsKey(Integer.valueOf(p_70644_1_.field_76415_H));
   }

   public PotionEffect func_70660_b(Potion p_70660_1_) {
      return (PotionEffect)this.field_70713_bf.get(Integer.valueOf(p_70660_1_.field_76415_H));
   }

   public void func_70690_d(PotionEffect p_70690_1_) {
      if(this.func_70687_e(p_70690_1_)) {
         if(this.field_70713_bf.containsKey(Integer.valueOf(p_70690_1_.func_76456_a()))) {
            ((PotionEffect)this.field_70713_bf.get(Integer.valueOf(p_70690_1_.func_76456_a()))).func_76452_a(p_70690_1_);
            this.func_70695_b((PotionEffect)this.field_70713_bf.get(Integer.valueOf(p_70690_1_.func_76456_a())), true);
         } else {
            this.field_70713_bf.put(Integer.valueOf(p_70690_1_.func_76456_a()), p_70690_1_);
            this.func_70670_a(p_70690_1_);
         }

      }
   }

   public boolean func_70687_e(PotionEffect p_70687_1_) {
      if(this.func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
         int var2 = p_70687_1_.func_76456_a();
         if(var2 == Potion.field_76428_l.field_76415_H || var2 == Potion.field_76436_u.field_76415_H) {
            return false;
         }
      }

      return true;
   }

   public boolean func_70662_br() {
      return this.func_70668_bt() == EnumCreatureAttribute.UNDEAD;
   }

   @SideOnly(Side.CLIENT)
   public void func_70618_n(int p_70618_1_) {
      this.field_70713_bf.remove(Integer.valueOf(p_70618_1_));
   }

   public void func_82170_o(int p_82170_1_) {
      PotionEffect var2 = (PotionEffect)this.field_70713_bf.remove(Integer.valueOf(p_82170_1_));
      if(var2 != null) {
         this.func_70688_c(var2);
      }

   }

   protected void func_70670_a(PotionEffect p_70670_1_) {
      this.field_70752_e = true;
      if(!this.field_70170_p.field_72995_K) {
         Potion.field_76425_a[p_70670_1_.func_76456_a()].func_111185_a(this, this.func_110140_aT(), p_70670_1_.func_76458_c());
      }

   }

   protected void func_70695_b(PotionEffect p_70695_1_, boolean p_70695_2_) {
      this.field_70752_e = true;
      if(p_70695_2_ && !this.field_70170_p.field_72995_K) {
         Potion.field_76425_a[p_70695_1_.func_76456_a()].func_111187_a(this, this.func_110140_aT(), p_70695_1_.func_76458_c());
         Potion.field_76425_a[p_70695_1_.func_76456_a()].func_111185_a(this, this.func_110140_aT(), p_70695_1_.func_76458_c());
      }

   }

   protected void func_70688_c(PotionEffect p_70688_1_) {
      this.field_70752_e = true;
      if(!this.field_70170_p.field_72995_K) {
         Potion.field_76425_a[p_70688_1_.func_76456_a()].func_111187_a(this, this.func_110140_aT(), p_70688_1_.func_76458_c());
      }

   }

   public void func_70691_i(float p_70691_1_) {
      float var2 = this.func_110143_aJ();
      if(var2 > 0.0F) {
         this.func_70606_j(var2 + p_70691_1_);
      }

   }

   public final float func_110143_aJ() {
      return this.field_70180_af.func_111145_d(6);
   }

   public void func_70606_j(float p_70606_1_) {
      this.field_70180_af.func_75692_b(6, Float.valueOf(MathHelper.func_76131_a(p_70606_1_, 0.0F, this.func_110138_aP())));
   }

   public boolean func_70097_a(DamageSource p_70097_1_, float p_70097_2_) {
      if(this.func_85032_ar()) {
         return false;
      } else if(this.field_70170_p.field_72995_K) {
         return false;
      } else {
         this.field_70708_bq = 0;
         if(this.func_110143_aJ() <= 0.0F) {
            return false;
         } else if(p_70097_1_.func_76347_k() && this.func_70644_a(Potion.field_76426_n)) {
            return false;
         } else {
            if((p_70097_1_ == DamageSource.field_82728_o || p_70097_1_ == DamageSource.field_82729_p) && this.func_71124_b(4) != null) {
               this.func_71124_b(4).func_77972_a((int)(p_70097_2_ * 4.0F + this.field_70146_Z.nextFloat() * p_70097_2_ * 2.0F), this);
               p_70097_2_ *= 0.75F;
            }

            this.field_70721_aZ = 1.5F;
            boolean var3 = true;
            if((float)this.field_70172_ad > (float)this.field_70771_an / 2.0F) {
               if(p_70097_2_ <= this.field_110153_bc) {
                  return false;
               }

               this.func_70665_d(p_70097_1_, p_70097_2_ - this.field_110153_bc);
               this.field_110153_bc = p_70097_2_;
               var3 = false;
            } else {
               this.field_110153_bc = p_70097_2_;
               this.field_70735_aL = this.func_110143_aJ();
               this.field_70172_ad = this.field_70771_an;
               this.func_70665_d(p_70097_1_, p_70097_2_);
               this.field_70737_aN = this.field_70738_aO = 10;
            }

            this.field_70739_aP = 0.0F;
            Entity var4 = p_70097_1_.func_76346_g();
            if(var4 != null) {
               if(var4 instanceof EntityLivingBase) {
                  this.func_70604_c((EntityLivingBase)var4);
               }

               if(var4 instanceof EntityPlayer) {
                  this.field_70718_bc = 100;
                  this.field_70717_bb = (EntityPlayer)var4;
               } else if(var4 instanceof EntityWolf) {
                  EntityWolf var5 = (EntityWolf)var4;
                  if(var5.func_70909_n()) {
                     this.field_70718_bc = 100;
                     this.field_70717_bb = null;
                  }
               }
            }

            if(var3) {
               this.field_70170_p.func_72960_a(this, (byte)2);
               if(p_70097_1_ != DamageSource.field_76369_e) {
                  this.func_70018_K();
               }

               if(var4 != null) {
                  double var9 = var4.field_70165_t - this.field_70165_t;

                  double var7;
                  for(var7 = var4.field_70161_v - this.field_70161_v; var9 * var9 + var7 * var7 < 1.0E-4D; var7 = (Math.random() - Math.random()) * 0.01D) {
                     var9 = (Math.random() - Math.random()) * 0.01D;
                  }

                  this.field_70739_aP = (float)(Math.atan2(var7, var9) * 180.0D / 3.1415927410125732D) - this.field_70177_z;
                  this.func_70653_a(var4, p_70097_2_, var9, var7);
               } else {
                  this.field_70739_aP = (float)((int)(Math.random() * 2.0D) * 180);
               }
            }

            if(this.func_110143_aJ() <= 0.0F) {
               if(var3) {
                  this.func_85030_a(this.func_70673_aS(), this.func_70599_aP(), this.func_70647_i());
               }

               this.func_70645_a(p_70097_1_);
            } else if(var3) {
               this.func_85030_a(this.func_70621_aR(), this.func_70599_aP(), this.func_70647_i());
            }

            return true;
         }
      }
   }

   public void func_70669_a(ItemStack p_70669_1_) {
      this.func_85030_a("random.break", 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);

      for(int var2 = 0; var2 < 5; ++var2) {
         Vec3 var3 = this.field_70170_p.func_82732_R().func_72345_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         var3.func_72440_a(-this.field_70125_A * 3.1415927F / 180.0F);
         var3.func_72442_b(-this.field_70177_z * 3.1415927F / 180.0F);
         Vec3 var4 = this.field_70170_p.func_82732_R().func_72345_a(((double)this.field_70146_Z.nextFloat() - 0.5D) * 0.3D, (double)(-this.field_70146_Z.nextFloat()) * 0.6D - 0.3D, 0.6D);
         var4.func_72440_a(-this.field_70125_A * 3.1415927F / 180.0F);
         var4.func_72442_b(-this.field_70177_z * 3.1415927F / 180.0F);
         var4 = var4.func_72441_c(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v);
         this.field_70170_p.func_72869_a("iconcrack_" + p_70669_1_.func_77973_b().field_77779_bT, var4.field_72450_a, var4.field_72448_b, var4.field_72449_c, var3.field_72450_a, var3.field_72448_b + 0.05D, var3.field_72449_c);
      }

   }

   public void func_70645_a(DamageSource p_70645_1_) {
      Entity var2 = p_70645_1_.func_76346_g();
      EntityLivingBase var3 = this.func_94060_bK();
      if(this.field_70744_aE >= 0 && var3 != null) {
         var3.func_70084_c(this, this.field_70744_aE);
      }

      if(var2 != null) {
         var2.func_70074_a(this);
      }

      this.field_70729_aU = true;
      if(!this.field_70170_p.field_72995_K) {
         int var4 = 0;
         if(var2 instanceof EntityPlayer) {
            var4 = EnchantmentHelper.func_77519_f((EntityLivingBase)var2);
         }

         if(!this.func_70631_g_() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
            this.func_70628_a(this.field_70718_bc > 0, var4);
            this.func_82160_b(this.field_70718_bc > 0, var4);
            if(this.field_70718_bc > 0) {
               int var5 = this.field_70146_Z.nextInt(200) - var4;
               if(var5 < 5) {
                  this.func_70600_l(var5 <= 0?1:0);
               }
            }
         }
      }

      this.field_70170_p.func_72960_a(this, (byte)3);
   }

   protected void func_82160_b(boolean p_82160_1_, int p_82160_2_) {}

   public void func_70653_a(Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
      if(this.field_70146_Z.nextDouble() >= this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e()) {
         this.field_70160_al = true;
         float var7 = MathHelper.func_76133_a(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_);
         float var8 = 0.4F;
         this.field_70159_w /= 2.0D;
         this.field_70181_x /= 2.0D;
         this.field_70179_y /= 2.0D;
         this.field_70159_w -= p_70653_3_ / (double)var7 * (double)var8;
         this.field_70181_x += (double)var8;
         this.field_70179_y -= p_70653_5_ / (double)var7 * (double)var8;
         if(this.field_70181_x > 0.4000000059604645D) {
            this.field_70181_x = 0.4000000059604645D;
         }

      }
   }

   protected String func_70621_aR() {
      return "damage.hit";
   }

   protected String func_70673_aS() {
      return "damage.hit";
   }

   protected void func_70600_l(int p_70600_1_) {}

   protected void func_70628_a(boolean p_70628_1_, int p_70628_2_) {}

   public boolean func_70617_f_() {
      int var1 = MathHelper.func_76128_c(this.field_70165_t);
      int var2 = MathHelper.func_76128_c(this.field_70121_D.field_72338_b);
      int var3 = MathHelper.func_76128_c(this.field_70161_v);
      int var4 = this.field_70170_p.func_72798_a(var1, var2, var3);
      return var4 == Block.field_72055_aF.field_71990_ca || var4 == Block.field_71998_bu.field_71990_ca;
   }

   public boolean func_70089_S() {
      return !this.field_70128_L && this.func_110143_aJ() > 0.0F;
   }

   protected void func_70069_a(float p_70069_1_) {
      super.func_70069_a(p_70069_1_);
      PotionEffect var2 = this.func_70660_b(Potion.field_76430_j);
      float var3 = var2 != null?(float)(var2.func_76458_c() + 1):0.0F;
      int var4 = MathHelper.func_76123_f(p_70069_1_ - 3.0F - var3);
      if(var4 > 0) {
         if(var4 > 4) {
            this.func_85030_a("damage.fallbig", 1.0F, 1.0F);
         } else {
            this.func_85030_a("damage.fallsmall", 1.0F, 1.0F);
         }

         this.func_70097_a(DamageSource.field_76379_h, (float)var4);
         int var5 = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D - (double)this.field_70129_M), MathHelper.func_76128_c(this.field_70161_v));
         if(var5 > 0) {
            StepSound var6 = Block.field_71973_m[var5].field_72020_cn;
            this.func_85030_a(var6.func_72675_d(), var6.func_72677_b() * 0.5F, var6.func_72678_c() * 0.75F);
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_70057_ab() {
      this.field_70737_aN = this.field_70738_aO = 10;
      this.field_70739_aP = 0.0F;
   }

   public int func_70658_aO() {
      int var1 = 0;
      ItemStack[] var2 = this.func_70035_c();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ItemStack var5 = var2[var4];
         if(var5 != null && var5.func_77973_b() instanceof ItemArmor) {
            int var6 = ((ItemArmor)var5.func_77973_b()).field_77879_b;
            var1 += var6;
         }
      }

      return var1;
   }

   protected void func_70675_k(float p_70675_1_) {}

   protected float func_70655_b(DamageSource p_70655_1_, float p_70655_2_) {
      if(!p_70655_1_.func_76363_c()) {
         int var3 = 25 - this.func_70658_aO();
         float var4 = p_70655_2_ * (float)var3;
         this.func_70675_k(p_70655_2_);
         p_70655_2_ = var4 / 25.0F;
      }

      return p_70655_2_;
   }

   protected float func_70672_c(DamageSource p_70672_1_, float p_70672_2_) {
      if(this instanceof EntityZombie) {
         p_70672_2_ = p_70672_2_;
      }

      int var3;
      int var4;
      float var5;
      if(this.func_70644_a(Potion.field_76429_m) && p_70672_1_ != DamageSource.field_76380_i) {
         var3 = (this.func_70660_b(Potion.field_76429_m).func_76458_c() + 1) * 5;
         var4 = 25 - var3;
         var5 = p_70672_2_ * (float)var4;
         p_70672_2_ = var5 / 25.0F;
      }

      if(p_70672_2_ <= 0.0F) {
         return 0.0F;
      } else {
         var3 = EnchantmentHelper.func_77508_a(this.func_70035_c(), p_70672_1_);
         if(var3 > 20) {
            var3 = 20;
         }

         if(var3 > 0 && var3 <= 20) {
            var4 = 25 - var3;
            var5 = p_70672_2_ * (float)var4;
            p_70672_2_ = var5 / 25.0F;
         }

         return p_70672_2_;
      }
   }

   protected void func_70665_d(DamageSource p_70665_1_, float p_70665_2_) {
      if(!this.func_85032_ar()) {
         p_70665_2_ = this.func_70655_b(p_70665_1_, p_70665_2_);
         p_70665_2_ = this.func_70672_c(p_70665_1_, p_70665_2_);
         float var3 = p_70665_2_;
         p_70665_2_ = Math.max(p_70665_2_ - this.func_110139_bj(), 0.0F);
         this.func_110149_m(this.func_110139_bj() - (var3 - p_70665_2_));
         if(p_70665_2_ != 0.0F) {
            float var4 = this.func_110143_aJ();
            this.func_70606_j(var4 - p_70665_2_);
            this.func_110142_aN().func_94547_a(p_70665_1_, var4, p_70665_2_);
            this.func_110149_m(this.func_110139_bj() - p_70665_2_);
         }
      }
   }

   public CombatTracker func_110142_aN() {
      return this.field_94063_bt;
   }

   public EntityLivingBase func_94060_bK() {
      return (EntityLivingBase)(this.field_94063_bt.func_94550_c() != null?this.field_94063_bt.func_94550_c():(this.field_70717_bb != null?this.field_70717_bb:(this.field_70755_b != null?this.field_70755_b:null)));
   }

   public final float func_110138_aP() {
      return (float)this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111126_e();
   }

   public final int func_85035_bI() {
      return this.field_70180_af.func_75683_a(9);
   }

   public final void func_85034_r(int p_85034_1_) {
      this.field_70180_af.func_75692_b(9, Byte.valueOf((byte)p_85034_1_));
   }

   private int func_82166_i() {
      return this.func_70644_a(Potion.field_76422_e)?6 - (1 + this.func_70660_b(Potion.field_76422_e).func_76458_c()) * 1:(this.func_70644_a(Potion.field_76419_f)?6 + (1 + this.func_70660_b(Potion.field_76419_f).func_76458_c()) * 2:6);
   }

   public void func_71038_i() {
      if(!this.field_82175_bq || this.field_110158_av >= this.func_82166_i() / 2 || this.field_110158_av < 0) {
         this.field_110158_av = -1;
         this.field_82175_bq = true;
         if(this.field_70170_p instanceof WorldServer) {
            ((WorldServer)this.field_70170_p).func_73039_n().func_72784_a(this, new Packet18Animation(this, 1));
         }
      }

   }

   @SideOnly(Side.CLIENT)
   public void func_70103_a(byte p_70103_1_) {
      if(p_70103_1_ == 2) {
         this.field_70721_aZ = 1.5F;
         this.field_70172_ad = this.field_70771_an;
         this.field_70737_aN = this.field_70738_aO = 10;
         this.field_70739_aP = 0.0F;
         this.func_85030_a(this.func_70621_aR(), this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         this.func_70097_a(DamageSource.field_76377_j, 0.0F);
      } else if(p_70103_1_ == 3) {
         this.func_85030_a(this.func_70673_aS(), this.func_70599_aP(), (this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F);
         this.func_70606_j(0.0F);
         this.func_70645_a(DamageSource.field_76377_j);
      } else {
         super.func_70103_a(p_70103_1_);
      }

   }

   protected void func_70076_C() {
      this.func_70097_a(DamageSource.field_76380_i, 4.0F);
   }

   protected void func_82168_bl() {
      int var1 = this.func_82166_i();
      if(this.field_82175_bq) {
         ++this.field_110158_av;
         if(this.field_110158_av >= var1) {
            this.field_110158_av = 0;
            this.field_82175_bq = false;
         }
      } else {
         this.field_110158_av = 0;
      }

      this.field_70733_aJ = (float)this.field_110158_av / (float)var1;
   }

   public AttributeInstance func_110148_a(Attribute p_110148_1_) {
      return this.func_110140_aT().func_111151_a(p_110148_1_);
   }

   public BaseAttributeMap func_110140_aT() {
      if(this.field_110155_d == null) {
         this.field_110155_d = new ServersideAttributeMap();
      }

      return this.field_110155_d;
   }

   public EnumCreatureAttribute func_70668_bt() {
      return EnumCreatureAttribute.UNDEFINED;
   }

   public abstract ItemStack func_70694_bm();

   public abstract ItemStack func_71124_b(int var1);

   public abstract void func_70062_b(int var1, ItemStack var2);

   public void func_70031_b(boolean p_70031_1_) {
      super.func_70031_b(p_70031_1_);
      AttributeInstance var2 = this.func_110148_a(SharedMonsterAttributes.field_111263_d);
      if(var2.func_111127_a(field_110156_b) != null) {
         var2.func_111124_b(field_110157_c);
      }

      if(p_70031_1_) {
         var2.func_111121_a(field_110157_c);
      }

   }

   public abstract ItemStack[] func_70035_c();

   protected float func_70599_aP() {
      return 1.0F;
   }

   protected float func_70647_i() {
      return this.func_70631_g_()?(this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.5F:(this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean func_70610_aX() {
      return this.func_110143_aJ() <= 0.0F;
   }

   public void func_70634_a(double p_70634_1_, double p_70634_3_, double p_70634_5_) {
      this.func_70012_b(p_70634_1_, p_70634_3_, p_70634_5_, this.field_70177_z, this.field_70125_A);
   }

   public void func_110145_l(Entity p_110145_1_) {
      double var3 = p_110145_1_.field_70165_t;
      double var5 = p_110145_1_.field_70121_D.field_72338_b + (double)p_110145_1_.field_70131_O;
      double var7 = p_110145_1_.field_70161_v;

      for(double var9 = -1.5D; var9 < 2.0D; ++var9) {
         for(double var11 = -1.5D; var11 < 2.0D; ++var11) {
            if(var9 != 0.0D || var11 != 0.0D) {
               int var13 = (int)(this.field_70165_t + var9);
               int var14 = (int)(this.field_70161_v + var11);
               AxisAlignedBB var2 = this.field_70121_D.func_72325_c(var9, 1.0D, var11);
               if(this.field_70170_p.func_72840_a(var2).isEmpty()) {
                  if(this.field_70170_p.func_72797_t(var13, (int)this.field_70163_u, var14)) {
                     this.func_70634_a(this.field_70165_t + var9, this.field_70163_u + 1.0D, this.field_70161_v + var11);
                     return;
                  }

                  if(this.field_70170_p.func_72797_t(var13, (int)this.field_70163_u - 1, var14) || this.field_70170_p.func_72803_f(var13, (int)this.field_70163_u - 1, var14) == Material.field_76244_g) {
                     var3 = this.field_70165_t + var9;
                     var5 = this.field_70163_u + 1.0D;
                     var7 = this.field_70161_v + var11;
                  }
               }
            }
         }
      }

      this.func_70634_a(var3, var5, var7);
   }

   @SideOnly(Side.CLIENT)
   public boolean func_94059_bO() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_70620_b(ItemStack p_70620_1_, int p_70620_2_) {
      return p_70620_1_.func_77954_c();
   }

   protected void func_70664_aZ() {
      this.field_70181_x = 0.41999998688697815D;
      if(this.func_70644_a(Potion.field_76430_j)) {
         this.field_70181_x += (double)((float)(this.func_70660_b(Potion.field_76430_j).func_76458_c() + 1) * 0.1F);
      }

      if(this.func_70051_ag()) {
         float var1 = this.field_70177_z * 0.017453292F;
         this.field_70159_w -= (double)(MathHelper.func_76126_a(var1) * 0.2F);
         this.field_70179_y += (double)(MathHelper.func_76134_b(var1) * 0.2F);
      }

      this.field_70160_al = true;
   }

   public void func_70612_e(float p_70612_1_, float p_70612_2_) {
      double var10;
      if(this.func_70090_H() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).field_71075_bZ.field_75100_b)) {
         var10 = this.field_70163_u;
         this.func_70060_a(p_70612_1_, p_70612_2_, this.func_70650_aV()?0.04F:0.02F);
         this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.800000011920929D;
         this.field_70181_x *= 0.800000011920929D;
         this.field_70179_y *= 0.800000011920929D;
         this.field_70181_x -= 0.02D;
         if(this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + var10, this.field_70179_y)) {
            this.field_70181_x = 0.30000001192092896D;
         }
      } else if(this.func_70058_J() && (!(this instanceof EntityPlayer) || !((EntityPlayer)this).field_71075_bZ.field_75100_b)) {
         var10 = this.field_70163_u;
         this.func_70060_a(p_70612_1_, p_70612_2_, 0.02F);
         this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
         this.field_70159_w *= 0.5D;
         this.field_70181_x *= 0.5D;
         this.field_70179_y *= 0.5D;
         this.field_70181_x -= 0.02D;
         if(this.field_70123_F && this.func_70038_c(this.field_70159_w, this.field_70181_x + 0.6000000238418579D - this.field_70163_u + var10, this.field_70179_y)) {
            this.field_70181_x = 0.30000001192092896D;
         }
      } else {
         float var3 = 0.91F;
         if(this.field_70122_E) {
            var3 = 0.54600006F;
            int var4 = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));
            if(var4 > 0) {
               var3 = Block.field_71973_m[var4].field_72016_cq * 0.91F;
            }
         }

         float var8 = 0.16277136F / (var3 * var3 * var3);
         float var5;
         if(this.field_70122_E) {
            var5 = this.func_70689_ay() * var8;
         } else {
            var5 = this.field_70747_aH;
         }

         this.func_70060_a(p_70612_1_, p_70612_2_, var5);
         var3 = 0.91F;
         if(this.field_70122_E) {
            var3 = 0.54600006F;
            int var6 = this.field_70170_p.func_72798_a(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70121_D.field_72338_b) - 1, MathHelper.func_76128_c(this.field_70161_v));
            if(var6 > 0) {
               var3 = Block.field_71973_m[var6].field_72016_cq * 0.91F;
            }
         }

         if(this.func_70617_f_()) {
            float var11 = 0.15F;
            if(this.field_70159_w < (double)(-var11)) {
               this.field_70159_w = (double)(-var11);
            }

            if(this.field_70159_w > (double)var11) {
               this.field_70159_w = (double)var11;
            }

            if(this.field_70179_y < (double)(-var11)) {
               this.field_70179_y = (double)(-var11);
            }

            if(this.field_70179_y > (double)var11) {
               this.field_70179_y = (double)var11;
            }

            this.field_70143_R = 0.0F;
            if(this.field_70181_x < -0.15D) {
               this.field_70181_x = -0.15D;
            }

            boolean var7 = this.func_70093_af() && this instanceof EntityPlayer;
            if(var7 && this.field_70181_x < 0.0D) {
               this.field_70181_x = 0.0D;
            }
         }

         this.func_70091_d(this.field_70159_w, this.field_70181_x, this.field_70179_y);
         if(this.field_70123_F && this.func_70617_f_()) {
            this.field_70181_x = 0.2D;
         }

         if(this.field_70170_p.field_72995_K && (!this.field_70170_p.func_72899_e((int)this.field_70165_t, 0, (int)this.field_70161_v) || !this.field_70170_p.func_72938_d((int)this.field_70165_t, (int)this.field_70161_v).field_76636_d)) {
            if(this.field_70163_u > 0.0D) {
               this.field_70181_x = -0.1D;
            } else {
               this.field_70181_x = 0.0D;
            }
         } else {
            this.field_70181_x -= 0.08D;
         }

         this.field_70181_x *= 0.9800000190734863D;
         this.field_70159_w *= (double)var3;
         this.field_70179_y *= (double)var3;
      }

      this.field_70722_aY = this.field_70721_aZ;
      var10 = this.field_70165_t - this.field_70169_q;
      double var9 = this.field_70161_v - this.field_70166_s;
      float var12 = MathHelper.func_76133_a(var10 * var10 + var9 * var9) * 4.0F;
      if(var12 > 1.0F) {
         var12 = 1.0F;
      }

      this.field_70721_aZ += (var12 - this.field_70721_aZ) * 0.4F;
      this.field_70754_ba += this.field_70721_aZ;
   }

   protected boolean func_70650_aV() {
      return false;
   }

   public float func_70689_ay() {
      return this.func_70650_aV()?this.field_70746_aG:0.1F;
   }

   public void func_70659_e(float p_70659_1_) {
      this.field_70746_aG = p_70659_1_;
   }

   public boolean func_70652_k(Entity p_70652_1_) {
      this.func_130011_c(p_70652_1_);
      return false;
   }

   public boolean func_70608_bn() {
      return false;
   }

   public void func_70071_h_() {
      super.func_70071_h_();
      if(!this.field_70170_p.field_72995_K) {
         int var1 = this.func_85035_bI();
         if(var1 > 0) {
            if(this.field_70720_be <= 0) {
               this.field_70720_be = 20 * (30 - var1);
            }

            --this.field_70720_be;
            if(this.field_70720_be <= 0) {
               this.func_85034_r(var1 - 1);
            }
         }

         for(int var2 = 0; var2 < 5; ++var2) {
            ItemStack var3 = this.field_82180_bT[var2];
            ItemStack var4 = this.func_71124_b(var2);
            if(!ItemStack.func_77989_b(var4, var3)) {
               ((WorldServer)this.field_70170_p).func_73039_n().func_72784_a(this, new Packet5PlayerInventory(this.field_70157_k, var2, var4));
               if(var3 != null) {
                  this.field_110155_d.func_111148_a(var3.func_111283_C());
               }

               if(var4 != null) {
                  this.field_110155_d.func_111147_b(var4.func_111283_C());
               }

               this.field_82180_bT[var2] = var4 == null?null:var4.func_77946_l();
            }
         }
      }

      this.func_70636_d();
      double var9 = this.field_70165_t - this.field_70169_q;
      double var10 = this.field_70161_v - this.field_70166_s;
      float var5 = (float)(var9 * var9 + var10 * var10);
      float var6 = this.field_70761_aq;
      float var7 = 0.0F;
      this.field_70768_au = this.field_110154_aX;
      float var8 = 0.0F;
      if(var5 > 0.0025000002F) {
         var8 = 1.0F;
         var7 = (float)Math.sqrt((double)var5) * 3.0F;
         var6 = (float)Math.atan2(var10, var9) * 180.0F / 3.1415927F - 90.0F;
      }

      if(this.field_70733_aJ > 0.0F) {
         var6 = this.field_70177_z;
      }

      if(!this.field_70122_E) {
         var8 = 0.0F;
      }

      this.field_110154_aX += (var8 - this.field_110154_aX) * 0.3F;
      this.field_70170_p.field_72984_F.func_76320_a("headTurn");
      var7 = this.func_110146_f(var6, var7);
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("rangeChecks");

      while(this.field_70177_z - this.field_70126_B < -180.0F) {
         this.field_70126_B -= 360.0F;
      }

      while(this.field_70177_z - this.field_70126_B >= 180.0F) {
         this.field_70126_B += 360.0F;
      }

      while(this.field_70761_aq - this.field_70760_ar < -180.0F) {
         this.field_70760_ar -= 360.0F;
      }

      while(this.field_70761_aq - this.field_70760_ar >= 180.0F) {
         this.field_70760_ar += 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C < -180.0F) {
         this.field_70127_C -= 360.0F;
      }

      while(this.field_70125_A - this.field_70127_C >= 180.0F) {
         this.field_70127_C += 360.0F;
      }

      while(this.field_70759_as - this.field_70758_at < -180.0F) {
         this.field_70758_at -= 360.0F;
      }

      while(this.field_70759_as - this.field_70758_at >= 180.0F) {
         this.field_70758_at += 360.0F;
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70764_aw += var7;
   }

   protected float func_110146_f(float p_110146_1_, float p_110146_2_) {
      float var3 = MathHelper.func_76142_g(p_110146_1_ - this.field_70761_aq);
      this.field_70761_aq += var3 * 0.3F;
      float var4 = MathHelper.func_76142_g(this.field_70177_z - this.field_70761_aq);
      boolean var5 = var4 < -90.0F || var4 >= 90.0F;
      if(var4 < -75.0F) {
         var4 = -75.0F;
      }

      if(var4 >= 75.0F) {
         var4 = 75.0F;
      }

      this.field_70761_aq = this.field_70177_z - var4;
      if(var4 * var4 > 2500.0F) {
         this.field_70761_aq += var4 * 0.2F;
      }

      if(var5) {
         p_110146_2_ *= -1.0F;
      }

      return p_110146_2_;
   }

   public void func_70636_d() {
      if(this.field_70773_bE > 0) {
         --this.field_70773_bE;
      }

      if(this.field_70716_bi > 0) {
         double var1 = this.field_70165_t + (this.field_70709_bj - this.field_70165_t) / (double)this.field_70716_bi;
         double var3 = this.field_70163_u + (this.field_70710_bk - this.field_70163_u) / (double)this.field_70716_bi;
         double var5 = this.field_70161_v + (this.field_110152_bk - this.field_70161_v) / (double)this.field_70716_bi;
         double var7 = MathHelper.func_76138_g(this.field_70712_bm - (double)this.field_70177_z);
         this.field_70177_z = (float)((double)this.field_70177_z + var7 / (double)this.field_70716_bi);
         this.field_70125_A = (float)((double)this.field_70125_A + (this.field_70705_bn - (double)this.field_70125_A) / (double)this.field_70716_bi);
         --this.field_70716_bi;
         this.func_70107_b(var1, var3, var5);
         this.func_70101_b(this.field_70177_z, this.field_70125_A);
      } else if(!this.func_70613_aW()) {
         this.field_70159_w *= 0.98D;
         this.field_70181_x *= 0.98D;
         this.field_70179_y *= 0.98D;
      }

      if(Math.abs(this.field_70159_w) < 0.005D) {
         this.field_70159_w = 0.0D;
      }

      if(Math.abs(this.field_70181_x) < 0.005D) {
         this.field_70181_x = 0.0D;
      }

      if(Math.abs(this.field_70179_y) < 0.005D) {
         this.field_70179_y = 0.0D;
      }

      this.field_70170_p.field_72984_F.func_76320_a("ai");
      if(this.func_70610_aX()) {
         this.field_70703_bu = false;
         this.field_70702_br = 0.0F;
         this.field_70701_bs = 0.0F;
         this.field_70704_bt = 0.0F;
      } else if(this.func_70613_aW()) {
         if(this.func_70650_aV()) {
            this.field_70170_p.field_72984_F.func_76320_a("newAi");
            this.func_70619_bc();
            this.field_70170_p.field_72984_F.func_76319_b();
         } else {
            this.field_70170_p.field_72984_F.func_76320_a("oldAi");
            this.func_70626_be();
            this.field_70170_p.field_72984_F.func_76319_b();
            this.field_70759_as = this.field_70177_z;
         }
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("jump");
      if(this.field_70703_bu) {
         if(!this.func_70090_H() && !this.func_70058_J()) {
            if(this.field_70122_E && this.field_70773_bE == 0) {
               this.func_70664_aZ();
               this.field_70773_bE = 10;
            }
         } else {
            this.field_70181_x += 0.03999999910593033D;
         }
      } else {
         this.field_70773_bE = 0;
      }

      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("travel");
      this.field_70702_br *= 0.98F;
      this.field_70701_bs *= 0.98F;
      this.field_70704_bt *= 0.9F;
      this.func_70612_e(this.field_70702_br, this.field_70701_bs);
      this.field_70170_p.field_72984_F.func_76319_b();
      this.field_70170_p.field_72984_F.func_76320_a("push");
      if(!this.field_70170_p.field_72995_K) {
         this.func_85033_bc();
      }

      this.field_70170_p.field_72984_F.func_76319_b();
   }

   protected void func_70619_bc() {}

   protected void func_85033_bc() {
      List var1 = this.field_70170_p.func_72839_b(this, this.field_70121_D.func_72314_b(0.20000000298023224D, 0.0D, 0.20000000298023224D));
      if(var1 != null && !var1.isEmpty()) {
         for(int var2 = 0; var2 < var1.size(); ++var2) {
            Entity var3 = (Entity)var1.get(var2);
            if(var3.func_70104_M()) {
               this.func_82167_n(var3);
            }
         }
      }

   }

   protected void func_82167_n(Entity p_82167_1_) {
      p_82167_1_.func_70108_f(this);
   }

   public void func_70098_U() {
      super.func_70098_U();
      this.field_70768_au = this.field_110154_aX;
      this.field_110154_aX = 0.0F;
      this.field_70143_R = 0.0F;
   }

   @SideOnly(Side.CLIENT)
   public void func_70056_a(double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
      this.field_70129_M = 0.0F;
      this.field_70709_bj = p_70056_1_;
      this.field_70710_bk = p_70056_3_;
      this.field_110152_bk = p_70056_5_;
      this.field_70712_bm = (double)p_70056_7_;
      this.field_70705_bn = (double)p_70056_8_;
      this.field_70716_bi = p_70056_9_;
   }

   protected void func_70629_bd() {}

   protected void func_70626_be() {
      ++this.field_70708_bq;
   }

   public void func_70637_d(boolean p_70637_1_) {
      this.field_70703_bu = p_70637_1_;
   }

   public void func_71001_a(Entity p_71001_1_, int p_71001_2_) {
      if(!p_71001_1_.field_70128_L && !this.field_70170_p.field_72995_K) {
         EntityTracker var3 = ((WorldServer)this.field_70170_p).func_73039_n();
         if(p_71001_1_ instanceof EntityItem) {
            var3.func_72784_a(p_71001_1_, new Packet22Collect(p_71001_1_.field_70157_k, this.field_70157_k));
         }

         if(p_71001_1_ instanceof EntityArrow) {
            var3.func_72784_a(p_71001_1_, new Packet22Collect(p_71001_1_.field_70157_k, this.field_70157_k));
         }

         if(p_71001_1_ instanceof EntityXPOrb) {
            var3.func_72784_a(p_71001_1_, new Packet22Collect(p_71001_1_.field_70157_k, this.field_70157_k));
         }
      }

   }

   public boolean func_70685_l(Entity p_70685_1_) {
      return this.field_70170_p.func_72933_a(this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u + (double)this.func_70047_e(), this.field_70161_v), this.field_70170_p.func_82732_R().func_72345_a(p_70685_1_.field_70165_t, p_70685_1_.field_70163_u + (double)p_70685_1_.func_70047_e(), p_70685_1_.field_70161_v)) == null;
   }

   public Vec3 func_70040_Z() {
      return this.func_70676_i(1.0F);
   }

   public Vec3 func_70676_i(float p_70676_1_) {
      float var2;
      float var3;
      float var4;
      float var5;
      if(p_70676_1_ == 1.0F) {
         var2 = MathHelper.func_76134_b(-this.field_70177_z * 0.017453292F - 3.1415927F);
         var3 = MathHelper.func_76126_a(-this.field_70177_z * 0.017453292F - 3.1415927F);
         var4 = -MathHelper.func_76134_b(-this.field_70125_A * 0.017453292F);
         var5 = MathHelper.func_76126_a(-this.field_70125_A * 0.017453292F);
         return this.field_70170_p.func_82732_R().func_72345_a((double)(var3 * var4), (double)var5, (double)(var2 * var4));
      } else {
         var2 = this.field_70127_C + (this.field_70125_A - this.field_70127_C) * p_70676_1_;
         var3 = this.field_70126_B + (this.field_70177_z - this.field_70126_B) * p_70676_1_;
         var4 = MathHelper.func_76134_b(-var3 * 0.017453292F - 3.1415927F);
         var5 = MathHelper.func_76126_a(-var3 * 0.017453292F - 3.1415927F);
         float var6 = -MathHelper.func_76134_b(-var2 * 0.017453292F);
         float var7 = MathHelper.func_76126_a(-var2 * 0.017453292F);
         return this.field_70170_p.func_82732_R().func_72345_a((double)(var5 * var6), (double)var7, (double)(var4 * var6));
      }
   }

   @SideOnly(Side.CLIENT)
   public float func_70678_g(float p_70678_1_) {
      float var2 = this.field_70733_aJ - this.field_70732_aI;
      if(var2 < 0.0F) {
         ++var2;
      }

      return this.field_70732_aI + var2 * p_70678_1_;
   }

   @SideOnly(Side.CLIENT)
   public Vec3 func_70666_h(float p_70666_1_) {
      if(p_70666_1_ == 1.0F) {
         return this.field_70170_p.func_82732_R().func_72345_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
      } else {
         double var2 = this.field_70169_q + (this.field_70165_t - this.field_70169_q) * (double)p_70666_1_;
         double var4 = this.field_70167_r + (this.field_70163_u - this.field_70167_r) * (double)p_70666_1_;
         double var6 = this.field_70166_s + (this.field_70161_v - this.field_70166_s) * (double)p_70666_1_;
         return this.field_70170_p.func_82732_R().func_72345_a(var2, var4, var6);
      }
   }

   @SideOnly(Side.CLIENT)
   public MovingObjectPosition func_70614_a(double p_70614_1_, float p_70614_3_) {
      Vec3 var4 = this.func_70666_h(p_70614_3_);
      Vec3 var5 = this.func_70676_i(p_70614_3_);
      Vec3 var6 = var4.func_72441_c(var5.field_72450_a * p_70614_1_, var5.field_72448_b * p_70614_1_, var5.field_72449_c * p_70614_1_);
      return this.field_70170_p.func_72933_a(var4, var6);
   }

   public boolean func_70613_aW() {
      return !this.field_70170_p.field_72995_K;
   }

   public boolean func_70067_L() {
      return !this.field_70128_L;
   }

   public boolean func_70104_M() {
      return !this.field_70128_L;
   }

   public float func_70047_e() {
      return this.field_70131_O * 0.85F;
   }

   protected void func_70018_K() {
      this.field_70133_I = this.field_70146_Z.nextDouble() >= this.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111126_e();
   }

   public float func_70079_am() {
      return this.field_70759_as;
   }

   @SideOnly(Side.CLIENT)
   public void func_70034_d(float p_70034_1_) {
      this.field_70759_as = p_70034_1_;
   }

   public float func_110139_bj() {
      return this.field_110151_bq;
   }

   public void func_110149_m(float p_110149_1_) {
      if(p_110149_1_ < 0.0F) {
         p_110149_1_ = 0.0F;
      }

      this.field_110151_bq = p_110149_1_;
   }

   public Team func_96124_cp() {
      return null;
   }

   public boolean func_142014_c(EntityLivingBase p_142014_1_) {
      return this.func_142012_a(p_142014_1_.func_96124_cp());
   }

   public boolean func_142012_a(Team p_142012_1_) {
      return this.func_96124_cp() != null?this.func_96124_cp().func_142054_a(p_142012_1_):false;
   }

}
