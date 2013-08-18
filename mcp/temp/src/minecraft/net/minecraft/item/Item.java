package net.minecraft.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemCarrotOnAStick;
import net.minecraft.item.ItemCoal;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemEmptyMap;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemEnderEye;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemFireworkCharge;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemHangingEntity;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemLeash;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemRedstone;
import net.minecraft.item.ItemReed;
import net.minecraft.item.ItemSaddle;
import net.minecraft.item.ItemSeedFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSign;
import net.minecraft.item.ItemSimpleFoiled;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemSnowball;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class Item {

   protected static final UUID field_111210_e = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
   private CreativeTabs field_77701_a;
   protected static Random field_77697_d = new Random();
   public static Item[] field_77698_e = new Item[32000];
   public static Item field_77695_f = (new ItemSpade(0, EnumToolMaterial.IRON)).func_77655_b("shovelIron").func_111206_d("iron_shovel");
   public static Item field_77696_g = (new ItemPickaxe(1, EnumToolMaterial.IRON)).func_77655_b("pickaxeIron").func_111206_d("iron_pickaxe");
   public static Item field_77708_h = (new ItemAxe(2, EnumToolMaterial.IRON)).func_77655_b("hatchetIron").func_111206_d("iron_axe");
   public static Item field_77709_i = (new ItemFlintAndSteel(3)).func_77655_b("flintAndSteel").func_111206_d("flint_and_steel");
   public static Item field_77706_j = (new ItemFood(4, 4, 0.3F, false)).func_77655_b("apple").func_111206_d("apple");
   public static ItemBow field_77707_k = (ItemBow)(new ItemBow(5)).func_77655_b("bow").func_111206_d("bow");
   public static Item field_77704_l = (new Item(6)).func_77655_b("arrow").func_77637_a(CreativeTabs.field_78037_j).func_111206_d("arrow");
   public static Item field_77705_m = (new ItemCoal(7)).func_77655_b("coal").func_111206_d("coal");
   public static Item field_77702_n = (new Item(8)).func_77655_b("diamond").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("diamond");
   public static Item field_77703_o = (new Item(9)).func_77655_b("ingotIron").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("iron_ingot");
   public static Item field_77717_p = (new Item(10)).func_77655_b("ingotGold").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("gold_ingot");
   public static Item field_77716_q = (new ItemSword(11, EnumToolMaterial.IRON)).func_77655_b("swordIron").func_111206_d("iron_sword");
   public static Item field_77715_r = (new ItemSword(12, EnumToolMaterial.WOOD)).func_77655_b("swordWood").func_111206_d("wood_sword");
   public static Item field_77714_s = (new ItemSpade(13, EnumToolMaterial.WOOD)).func_77655_b("shovelWood").func_111206_d("wood_shovel");
   public static Item field_77713_t = (new ItemPickaxe(14, EnumToolMaterial.WOOD)).func_77655_b("pickaxeWood").func_111206_d("wood_pickaxe");
   public static Item field_77712_u = (new ItemAxe(15, EnumToolMaterial.WOOD)).func_77655_b("hatchetWood").func_111206_d("wood_axe");
   public static Item field_77711_v = (new ItemSword(16, EnumToolMaterial.STONE)).func_77655_b("swordStone").func_111206_d("stone_sword");
   public static Item field_77710_w = (new ItemSpade(17, EnumToolMaterial.STONE)).func_77655_b("shovelStone").func_111206_d("stone_shovel");
   public static Item field_77720_x = (new ItemPickaxe(18, EnumToolMaterial.STONE)).func_77655_b("pickaxeStone").func_111206_d("stone_pickaxe");
   public static Item field_77719_y = (new ItemAxe(19, EnumToolMaterial.STONE)).func_77655_b("hatchetStone").func_111206_d("stone_axe");
   public static Item field_77718_z = (new ItemSword(20, EnumToolMaterial.EMERALD)).func_77655_b("swordDiamond").func_111206_d("diamond_sword");
   public static Item field_77673_A = (new ItemSpade(21, EnumToolMaterial.EMERALD)).func_77655_b("shovelDiamond").func_111206_d("diamond_shovel");
   public static Item field_77674_B = (new ItemPickaxe(22, EnumToolMaterial.EMERALD)).func_77655_b("pickaxeDiamond").func_111206_d("diamond_pickaxe");
   public static Item field_77675_C = (new ItemAxe(23, EnumToolMaterial.EMERALD)).func_77655_b("hatchetDiamond").func_111206_d("diamond_axe");
   public static Item field_77669_D = (new Item(24)).func_77664_n().func_77655_b("stick").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("stick");
   public static Item field_77670_E = (new Item(25)).func_77655_b("bowl").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("bowl");
   public static Item field_77671_F = (new ItemSoup(26, 6)).func_77655_b("mushroomStew").func_111206_d("mushroom_stew");
   public static Item field_77672_G = (new ItemSword(27, EnumToolMaterial.GOLD)).func_77655_b("swordGold").func_111206_d("gold_sword");
   public static Item field_77680_H = (new ItemSpade(28, EnumToolMaterial.GOLD)).func_77655_b("shovelGold").func_111206_d("gold_shovel");
   public static Item field_77681_I = (new ItemPickaxe(29, EnumToolMaterial.GOLD)).func_77655_b("pickaxeGold").func_111206_d("gold_pickaxe");
   public static Item field_77682_J = (new ItemAxe(30, EnumToolMaterial.GOLD)).func_77655_b("hatchetGold").func_111206_d("gold_axe");
   public static Item field_77683_K = (new ItemReed(31, Block.field_72062_bU)).func_77655_b("string").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("string");
   public static Item field_77676_L = (new Item(32)).func_77655_b("feather").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("feather");
   public static Item field_77677_M = (new Item(33)).func_77655_b("sulphur").func_77631_c(PotionHelper.field_77930_k).func_77637_a(CreativeTabs.field_78035_l).func_111206_d("gunpowder");
   public static Item field_77678_N = (new ItemHoe(34, EnumToolMaterial.WOOD)).func_77655_b("hoeWood").func_111206_d("wood_hoe");
   public static Item field_77679_O = (new ItemHoe(35, EnumToolMaterial.STONE)).func_77655_b("hoeStone").func_111206_d("stone_hoe");
   public static Item field_77689_P = (new ItemHoe(36, EnumToolMaterial.IRON)).func_77655_b("hoeIron").func_111206_d("iron_hoe");
   public static Item field_77688_Q = (new ItemHoe(37, EnumToolMaterial.EMERALD)).func_77655_b("hoeDiamond").func_111206_d("diamond_hoe");
   public static Item field_77691_R = (new ItemHoe(38, EnumToolMaterial.GOLD)).func_77655_b("hoeGold").func_111206_d("gold_hoe");
   public static Item field_77690_S = (new ItemSeeds(39, Block.field_72058_az.field_71990_ca, Block.field_72050_aA.field_71990_ca)).func_77655_b("seeds").func_111206_d("seeds_wheat");
   public static Item field_77685_T = (new Item(40)).func_77655_b("wheat").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("wheat");
   public static Item field_77684_U = (new ItemFood(41, 5, 0.6F, false)).func_77655_b("bread").func_111206_d("bread");
   public static ItemArmor field_77687_V = (ItemArmor)(new ItemArmor(42, EnumArmorMaterial.CLOTH, 0, 0)).func_77655_b("helmetCloth").func_111206_d("leather_helmet");
   public static ItemArmor field_77686_W = (ItemArmor)(new ItemArmor(43, EnumArmorMaterial.CLOTH, 0, 1)).func_77655_b("chestplateCloth").func_111206_d("leather_chestplate");
   public static ItemArmor field_77693_X = (ItemArmor)(new ItemArmor(44, EnumArmorMaterial.CLOTH, 0, 2)).func_77655_b("leggingsCloth").func_111206_d("leather_leggings");
   public static ItemArmor field_77692_Y = (ItemArmor)(new ItemArmor(45, EnumArmorMaterial.CLOTH, 0, 3)).func_77655_b("bootsCloth").func_111206_d("leather_boots");
   public static ItemArmor field_77694_Z = (ItemArmor)(new ItemArmor(46, EnumArmorMaterial.CHAIN, 1, 0)).func_77655_b("helmetChain").func_111206_d("chainmail_helmet");
   public static ItemArmor field_77814_aa = (ItemArmor)(new ItemArmor(47, EnumArmorMaterial.CHAIN, 1, 1)).func_77655_b("chestplateChain").func_111206_d("chainmail_chestplate");
   public static ItemArmor field_77816_ab = (ItemArmor)(new ItemArmor(48, EnumArmorMaterial.CHAIN, 1, 2)).func_77655_b("leggingsChain").func_111206_d("chainmail_leggings");
   public static ItemArmor field_77810_ac = (ItemArmor)(new ItemArmor(49, EnumArmorMaterial.CHAIN, 1, 3)).func_77655_b("bootsChain").func_111206_d("chainmail_boots");
   public static ItemArmor field_77812_ad = (ItemArmor)(new ItemArmor(50, EnumArmorMaterial.IRON, 2, 0)).func_77655_b("helmetIron").func_111206_d("iron_helmet");
   public static ItemArmor field_77822_ae = (ItemArmor)(new ItemArmor(51, EnumArmorMaterial.IRON, 2, 1)).func_77655_b("chestplateIron").func_111206_d("iron_chestplate");
   public static ItemArmor field_77824_af = (ItemArmor)(new ItemArmor(52, EnumArmorMaterial.IRON, 2, 2)).func_77655_b("leggingsIron").func_111206_d("iron_leggings");
   public static ItemArmor field_77818_ag = (ItemArmor)(new ItemArmor(53, EnumArmorMaterial.IRON, 2, 3)).func_77655_b("bootsIron").func_111206_d("iron_boots");
   public static ItemArmor field_77820_ah = (ItemArmor)(new ItemArmor(54, EnumArmorMaterial.DIAMOND, 3, 0)).func_77655_b("helmetDiamond").func_111206_d("diamond_helmet");
   public static ItemArmor field_77798_ai = (ItemArmor)(new ItemArmor(55, EnumArmorMaterial.DIAMOND, 3, 1)).func_77655_b("chestplateDiamond").func_111206_d("diamond_chestplate");
   public static ItemArmor field_77800_aj = (ItemArmor)(new ItemArmor(56, EnumArmorMaterial.DIAMOND, 3, 2)).func_77655_b("leggingsDiamond").func_111206_d("diamond_leggings");
   public static ItemArmor field_77794_ak = (ItemArmor)(new ItemArmor(57, EnumArmorMaterial.DIAMOND, 3, 3)).func_77655_b("bootsDiamond").func_111206_d("diamond_boots");
   public static ItemArmor field_77796_al = (ItemArmor)(new ItemArmor(58, EnumArmorMaterial.GOLD, 4, 0)).func_77655_b("helmetGold").func_111206_d("gold_helmet");
   public static ItemArmor field_77806_am = (ItemArmor)(new ItemArmor(59, EnumArmorMaterial.GOLD, 4, 1)).func_77655_b("chestplateGold").func_111206_d("gold_chestplate");
   public static ItemArmor field_77808_an = (ItemArmor)(new ItemArmor(60, EnumArmorMaterial.GOLD, 4, 2)).func_77655_b("leggingsGold").func_111206_d("gold_leggings");
   public static ItemArmor field_77802_ao = (ItemArmor)(new ItemArmor(61, EnumArmorMaterial.GOLD, 4, 3)).func_77655_b("bootsGold").func_111206_d("gold_boots");
   public static Item field_77804_ap = (new Item(62)).func_77655_b("flint").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("flint");
   public static Item field_77784_aq = (new ItemFood(63, 3, 0.3F, true)).func_77655_b("porkchopRaw").func_111206_d("porkchop_raw");
   public static Item field_77782_ar = (new ItemFood(64, 8, 0.8F, true)).func_77655_b("porkchopCooked").func_111206_d("porkchop_cooked");
   public static Item field_77780_as = (new ItemHangingEntity(65, EntityPainting.class)).func_77655_b("painting").func_111206_d("painting");
   public static Item field_77778_at = (new ItemAppleGold(66, 4, 1.2F, false)).func_77848_i().func_77844_a(Potion.field_76428_l.field_76415_H, 5, 1, 1.0F).func_77655_b("appleGold").func_111206_d("apple_golden");
   public static Item field_77792_au = (new ItemSign(67)).func_77655_b("sign").func_111206_d("sign");
   public static Item field_77790_av = (new ItemDoor(68, Material.field_76245_d)).func_77655_b("doorWood").func_111206_d("door_wood");
   public static Item field_77788_aw = (new ItemBucket(69, 0)).func_77655_b("bucket").func_77625_d(16).func_111206_d("bucket_empty");
   public static Item field_77786_ax = (new ItemBucket(70, Block.field_71942_A.field_71990_ca)).func_77655_b("bucketWater").func_77642_a(field_77788_aw).func_111206_d("bucket_water");
   public static Item field_77775_ay = (new ItemBucket(71, Block.field_71944_C.field_71990_ca)).func_77655_b("bucketLava").func_77642_a(field_77788_aw).func_111206_d("bucket_lava");
   public static Item field_77773_az = (new ItemMinecart(72, 0)).func_77655_b("minecart").func_111206_d("minecart_normal");
   public static Item field_77765_aA = (new ItemSaddle(73)).func_77655_b("saddle").func_111206_d("saddle");
   public static Item field_77766_aB = (new ItemDoor(74, Material.field_76243_f)).func_77655_b("doorIron").func_111206_d("door_iron");
   public static Item field_77767_aC = (new ItemRedstone(75)).func_77655_b("redstone").func_77631_c(PotionHelper.field_77932_i).func_111206_d("redstone_dust");
   public static Item field_77768_aD = (new ItemSnowball(76)).func_77655_b("snowball").func_111206_d("snowball");
   public static Item field_77769_aE = (new ItemBoat(77)).func_77655_b("boat").func_111206_d("boat");
   public static Item field_77770_aF = (new Item(78)).func_77655_b("leather").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("leather");
   public static Item field_77771_aG = (new ItemBucketMilk(79)).func_77655_b("milk").func_77642_a(field_77788_aw).func_111206_d("bucket_milk");
   public static Item field_77772_aH = (new Item(80)).func_77655_b("brick").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("brick");
   public static Item field_77757_aI = (new Item(81)).func_77655_b("clay").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("clay_ball");
   public static Item field_77758_aJ = (new ItemReed(82, Block.field_72040_aX)).func_77655_b("reeds").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("reeds");
   public static Item field_77759_aK = (new Item(83)).func_77655_b("paper").func_77637_a(CreativeTabs.field_78026_f).func_111206_d("paper");
   public static Item field_77760_aL = (new ItemBook(84)).func_77655_b("book").func_77637_a(CreativeTabs.field_78026_f).func_111206_d("book_normal");
   public static Item field_77761_aM = (new Item(85)).func_77655_b("slimeball").func_77637_a(CreativeTabs.field_78026_f).func_111206_d("slimeball");
   public static Item field_77762_aN = (new ItemMinecart(86, 1)).func_77655_b("minecartChest").func_111206_d("minecart_chest");
   public static Item field_77763_aO = (new ItemMinecart(87, 2)).func_77655_b("minecartFurnace").func_111206_d("minecart_furnace");
   public static Item field_77764_aP = (new ItemEgg(88)).func_77655_b("egg").func_111206_d("egg");
   public static Item field_77750_aQ = (new Item(89)).func_77655_b("compass").func_77637_a(CreativeTabs.field_78040_i).func_111206_d("compass");
   public static ItemFishingRod field_77749_aR = (ItemFishingRod)(new ItemFishingRod(90)).func_77655_b("fishingRod").func_111206_d("fishing_rod");
   public static Item field_77752_aS = (new Item(91)).func_77655_b("clock").func_77637_a(CreativeTabs.field_78040_i).func_111206_d("clock");
   public static Item field_77751_aT = (new Item(92)).func_77655_b("yellowDust").func_77631_c(PotionHelper.field_77929_j).func_77637_a(CreativeTabs.field_78035_l).func_111206_d("glowstone_dust");
   public static Item field_77754_aU = (new ItemFood(93, 2, 0.3F, false)).func_77655_b("fishRaw").func_111206_d("fish_raw");
   public static Item field_77753_aV = (new ItemFood(94, 5, 0.6F, false)).func_77655_b("fishCooked").func_111206_d("fish_cooked");
   public static Item field_77756_aW = (new ItemDye(95)).func_77655_b("dyePowder").func_111206_d("dye_powder");
   public static Item field_77755_aX = (new Item(96)).func_77655_b("bone").func_77664_n().func_77637_a(CreativeTabs.field_78026_f).func_111206_d("bone");
   public static Item field_77747_aY = (new Item(97)).func_77655_b("sugar").func_77631_c(PotionHelper.field_77922_b).func_77637_a(CreativeTabs.field_78035_l).func_111206_d("sugar");
   public static Item field_77746_aZ = (new ItemReed(98, Block.field_72009_bg)).func_77625_d(1).func_77655_b("cake").func_77637_a(CreativeTabs.field_78039_h).func_111206_d("cake");
   public static Item field_77776_ba = (new ItemBed(99)).func_77625_d(1).func_77655_b("bed").func_111206_d("bed");
   public static Item field_77742_bb = (new ItemReed(100, Block.field_72010_bh)).func_77655_b("diode").func_77637_a(CreativeTabs.field_78028_d).func_111206_d("repeater");
   public static Item field_77743_bc = (new ItemFood(101, 2, 0.1F, false)).func_77655_b("cookie").func_111206_d("cookie");
   public static ItemMap field_77744_bd = (ItemMap)(new ItemMap(102)).func_77655_b("map").func_111206_d("map_filled");
   public static ItemShears field_77745_be = (ItemShears)(new ItemShears(103)).func_77655_b("shears").func_111206_d("shears");
   public static Item field_77738_bf = (new ItemFood(104, 2, 0.3F, false)).func_77655_b("melon").func_111206_d("melon");
   public static Item field_77739_bg = (new ItemSeeds(105, Block.field_71996_bs.field_71990_ca, Block.field_72050_aA.field_71990_ca)).func_77655_b("seeds_pumpkin").func_111206_d("seeds_pumpkin");
   public static Item field_77740_bh = (new ItemSeeds(106, Block.field_71999_bt.field_71990_ca, Block.field_72050_aA.field_71990_ca)).func_77655_b("seeds_melon").func_111206_d("seeds_melon");
   public static Item field_77741_bi = (new ItemFood(107, 3, 0.3F, true)).func_77655_b("beefRaw").func_111206_d("beef_raw");
   public static Item field_77734_bj = (new ItemFood(108, 8, 0.8F, true)).func_77655_b("beefCooked").func_111206_d("beef_cooked");
   public static Item field_77735_bk = (new ItemFood(109, 2, 0.3F, true)).func_77844_a(Potion.field_76438_s.field_76415_H, 30, 0, 0.3F).func_77655_b("chickenRaw").func_111206_d("chicken_raw");
   public static Item field_77736_bl = (new ItemFood(110, 6, 0.6F, true)).func_77655_b("chickenCooked").func_111206_d("chicken_cooked");
   public static Item field_77737_bm = (new ItemFood(111, 4, 0.1F, true)).func_77844_a(Potion.field_76438_s.field_76415_H, 30, 0, 0.8F).func_77655_b("rottenFlesh").func_111206_d("rotten_flesh");
   public static Item field_77730_bn = (new ItemEnderPearl(112)).func_77655_b("enderPearl").func_111206_d("ender_pearl");
   public static Item field_77731_bo = (new Item(113)).func_77655_b("blazeRod").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("blaze_rod");
   public static Item field_77732_bp = (new Item(114)).func_77655_b("ghastTear").func_77631_c(PotionHelper.field_77923_c).func_77637_a(CreativeTabs.field_78038_k).func_111206_d("ghast_tear");
   public static Item field_77733_bq = (new Item(115)).func_77655_b("goldNugget").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("gold_nugget");
   public static Item field_77727_br = (new ItemSeeds(116, Block.field_72094_bD.field_71990_ca, Block.field_72013_bc.field_71990_ca)).func_77655_b("netherStalkSeeds").func_77631_c("+4").func_111206_d("nether_wart");
   public static ItemPotion field_77726_bs = (ItemPotion)(new ItemPotion(117)).func_77655_b("potion").func_111206_d("potion");
   public static Item field_77729_bt = (new ItemGlassBottle(118)).func_77655_b("glassBottle").func_111206_d("potion_bottle_empty");
   public static Item field_77728_bu = (new ItemFood(119, 2, 0.8F, false)).func_77844_a(Potion.field_76436_u.field_76415_H, 5, 0, 1.0F).func_77655_b("spiderEye").func_77631_c(PotionHelper.field_77920_d).func_111206_d("spider_eye");
   public static Item field_77723_bv = (new Item(120)).func_77655_b("fermentedSpiderEye").func_77631_c(PotionHelper.field_77921_e).func_77637_a(CreativeTabs.field_78038_k).func_111206_d("spider_eye_fermented");
   public static Item field_77722_bw = (new Item(121)).func_77655_b("blazePowder").func_77631_c(PotionHelper.field_77919_g).func_77637_a(CreativeTabs.field_78038_k).func_111206_d("blaze_powder");
   public static Item field_77725_bx = (new Item(122)).func_77655_b("magmaCream").func_77631_c(PotionHelper.field_77931_h).func_77637_a(CreativeTabs.field_78038_k).func_111206_d("magma_cream");
   public static Item field_77724_by = (new ItemReed(123, Block.field_72106_bF)).func_77655_b("brewingStand").func_77637_a(CreativeTabs.field_78038_k).func_111206_d("brewing_stand");
   public static Item field_77721_bz = (new ItemReed(124, Block.field_72108_bG)).func_77655_b("cauldron").func_77637_a(CreativeTabs.field_78038_k).func_111206_d("cauldron");
   public static Item field_77748_bA = (new ItemEnderEye(125)).func_77655_b("eyeOfEnder").func_111206_d("ender_eye");
   public static Item field_77813_bB = (new Item(126)).func_77655_b("speckledMelon").func_77631_c(PotionHelper.field_77918_f).func_77637_a(CreativeTabs.field_78038_k).func_111206_d("melon_speckled");
   public static Item field_77815_bC = (new ItemMonsterPlacer(127)).func_77655_b("monsterPlacer").func_111206_d("spawn_egg");
   public static Item field_77809_bD = (new ItemExpBottle(128)).func_77655_b("expBottle").func_111206_d("experience_bottle");
   public static Item field_77811_bE = (new ItemFireball(129)).func_77655_b("fireball").func_111206_d("fireball");
   public static Item field_77821_bF = (new ItemWritableBook(130)).func_77655_b("writingBook").func_77637_a(CreativeTabs.field_78026_f).func_111206_d("book_writable");
   public static Item field_77823_bG = (new ItemEditableBook(131)).func_77655_b("writtenBook").func_111206_d("book_written");
   public static Item field_77817_bH = (new Item(132)).func_77655_b("emerald").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("emerald");
   public static Item field_82802_bI = (new ItemHangingEntity(133, EntityItemFrame.class)).func_77655_b("frame").func_111206_d("item_frame");
   public static Item field_82796_bJ = (new ItemReed(134, Block.field_82516_cf)).func_77655_b("flowerPot").func_77637_a(CreativeTabs.field_78031_c).func_111206_d("flower_pot");
   public static Item field_82797_bK = (new ItemSeedFood(135, 4, 0.6F, Block.field_82513_cg.field_71990_ca, Block.field_72050_aA.field_71990_ca)).func_77655_b("carrots").func_111206_d("carrot");
   public static Item field_82794_bL = (new ItemSeedFood(136, 1, 0.3F, Block.field_82514_ch.field_71990_ca, Block.field_72050_aA.field_71990_ca)).func_77655_b("potato").func_111206_d("potato");
   public static Item field_82795_bM = (new ItemFood(137, 6, 0.6F, false)).func_77655_b("potatoBaked").func_111206_d("potato_baked");
   public static Item field_82800_bN = (new ItemFood(138, 2, 0.3F, false)).func_77844_a(Potion.field_76436_u.field_76415_H, 5, 0, 0.6F).func_77655_b("potatoPoisonous").func_111206_d("potato_poisonous");
   public static ItemEmptyMap field_82801_bO = (ItemEmptyMap)(new ItemEmptyMap(139)).func_77655_b("emptyMap").func_111206_d("map_empty");
   public static Item field_82798_bP = (new ItemFood(140, 6, 1.2F, false)).func_77655_b("carrotGolden").func_77631_c(PotionHelper.field_82818_l).func_111206_d("carrot_golden");
   public static Item field_82799_bQ = (new ItemSkull(141)).func_77655_b("skull").func_111206_d("skull");
   public static Item field_82793_bR = (new ItemCarrotOnAStick(142)).func_77655_b("carrotOnAStick").func_111206_d("carrot_on_a_stick");
   public static Item field_82792_bS = (new ItemSimpleFoiled(143)).func_77655_b("netherStar").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("nether_star");
   public static Item field_82791_bT = (new ItemFood(144, 8, 0.3F, false)).func_77655_b("pumpkinPie").func_77637_a(CreativeTabs.field_78039_h).func_111206_d("pumpkin_pie");
   public static Item field_92104_bU = (new ItemFirework(145)).func_77655_b("fireworks").func_111206_d("fireworks");
   public static Item field_92106_bV = (new ItemFireworkCharge(146)).func_77655_b("fireworksCharge").func_77637_a(CreativeTabs.field_78026_f).func_111206_d("fireworks_charge");
   public static ItemEnchantedBook field_92105_bW = (ItemEnchantedBook)(new ItemEnchantedBook(147)).func_77625_d(1).func_77655_b("enchantedBook").func_111206_d("book_enchanted");
   public static Item field_94585_bY = (new ItemReed(148, Block.field_94346_cn)).func_77655_b("comparator").func_77637_a(CreativeTabs.field_78028_d).func_111206_d("comparator");
   public static Item field_94584_bZ = (new Item(149)).func_77655_b("netherbrick").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("netherbrick");
   public static Item field_94583_ca = (new Item(150)).func_77655_b("netherquartz").func_77637_a(CreativeTabs.field_78035_l).func_111206_d("quartz");
   public static Item field_94582_cb = (new ItemMinecart(151, 3)).func_77655_b("minecartTnt").func_111206_d("minecart_tnt");
   public static Item field_96600_cc = (new ItemMinecart(152, 5)).func_77655_b("minecartHopper").func_111206_d("minecart_hopper");
   public static Item field_111215_ce = (new Item(161)).func_77655_b("horsearmormetal").func_77625_d(1).func_77637_a(CreativeTabs.field_78026_f).func_111206_d("iron_horse_armor");
   public static Item field_111216_cf = (new Item(162)).func_77655_b("horsearmorgold").func_77625_d(1).func_77637_a(CreativeTabs.field_78026_f).func_111206_d("gold_horse_armor");
   public static Item field_111213_cg = (new Item(163)).func_77655_b("horsearmordiamond").func_77625_d(1).func_77637_a(CreativeTabs.field_78026_f).func_111206_d("diamond_horse_armor");
   public static Item field_111214_ch = (new ItemLeash(164)).func_77655_b("leash").func_111206_d("lead");
   public static Item field_111212_ci = (new ItemNameTag(165)).func_77655_b("nameTag").func_111206_d("name_tag");
   public static Item field_77819_bI = (new ItemRecord(2000, "13")).func_77655_b("record").func_111206_d("record_13");
   public static Item field_77797_bJ = (new ItemRecord(2001, "cat")).func_77655_b("record").func_111206_d("record_cat");
   public static Item field_77799_bK = (new ItemRecord(2002, "blocks")).func_77655_b("record").func_111206_d("record_blocks");
   public static Item field_77793_bL = (new ItemRecord(2003, "chirp")).func_77655_b("record").func_111206_d("record_chirp");
   public static Item field_77795_bM = (new ItemRecord(2004, "far")).func_77655_b("record").func_111206_d("record_far");
   public static Item field_77805_bN = (new ItemRecord(2005, "mall")).func_77655_b("record").func_111206_d("record_mall");
   public static Item field_77807_bO = (new ItemRecord(2006, "mellohi")).func_77655_b("record").func_111206_d("record_mellohi");
   public static Item field_77801_bP = (new ItemRecord(2007, "stal")).func_77655_b("record").func_111206_d("record_stal");
   public static Item field_77803_bQ = (new ItemRecord(2008, "strad")).func_77655_b("record").func_111206_d("record_strad");
   public static Item field_77783_bR = (new ItemRecord(2009, "ward")).func_77655_b("record").func_111206_d("record_ward");
   public static Item field_77781_bS = (new ItemRecord(2010, "11")).func_77655_b("record").func_111206_d("record_11");
   public static Item field_85180_cf = (new ItemRecord(2011, "wait")).func_77655_b("record").func_111206_d("record_wait");
   public final int field_77779_bT;
   protected int field_77777_bU = 64;
   private int field_77699_b;
   protected boolean field_77789_bW;
   protected boolean field_77787_bX;
   private Item field_77700_c;
   private String field_77785_bY;
   private String field_77774_bZ;
   @SideOnly(Side.CLIENT)
   protected Icon field_77791_bV;
   protected String field_111218_cA;


   public Item(int p_i1875_1_) {
      this.field_77779_bT = 256 + p_i1875_1_;
      if(field_77698_e[256 + p_i1875_1_] != null) {
         System.out.println("CONFLICT @ " + p_i1875_1_);
      }

      field_77698_e[256 + p_i1875_1_] = this;
   }

   public Item func_77625_d(int p_77625_1_) {
      this.field_77777_bU = p_77625_1_;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public int func_94901_k() {
      return 1;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_77617_a(int p_77617_1_) {
      return this.field_77791_bV;
   }

   @SideOnly(Side.CLIENT)
   public final Icon func_77650_f(ItemStack p_77650_1_) {
      return this.func_77617_a(p_77650_1_.func_77960_j());
   }

   public boolean func_77648_a(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
      return false;
   }

   public float func_77638_a(ItemStack p_77638_1_, Block p_77638_2_) {
      return 1.0F;
   }

   public ItemStack func_77659_a(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
      return p_77659_1_;
   }

   public ItemStack func_77654_b(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
      return p_77654_1_;
   }

   public int func_77639_j() {
      return this.field_77777_bU;
   }

   public int func_77647_b(int p_77647_1_) {
      return 0;
   }

   public boolean func_77614_k() {
      return this.field_77787_bX;
   }

   protected Item func_77627_a(boolean p_77627_1_) {
      this.field_77787_bX = p_77627_1_;
      return this;
   }

   public int func_77612_l() {
      return this.field_77699_b;
   }

   public Item func_77656_e(int p_77656_1_) {
      this.field_77699_b = p_77656_1_;
      return this;
   }

   public boolean func_77645_m() {
      return this.field_77699_b > 0 && !this.field_77787_bX;
   }

   public boolean func_77644_a(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
      return false;
   }

   public boolean func_77660_a(ItemStack p_77660_1_, World p_77660_2_, int p_77660_3_, int p_77660_4_, int p_77660_5_, int p_77660_6_, EntityLivingBase p_77660_7_) {
      return false;
   }

   public boolean func_77641_a(Block p_77641_1_) {
      return false;
   }

   public boolean func_111207_a(ItemStack p_111207_1_, EntityPlayer p_111207_2_, EntityLivingBase p_111207_3_) {
      return false;
   }

   public Item func_77664_n() {
      this.field_77789_bW = true;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77662_d() {
      return this.field_77789_bW;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77629_n_() {
      return false;
   }

   public Item func_77655_b(String p_77655_1_) {
      this.field_77774_bZ = p_77655_1_;
      return this;
   }

   public String func_77657_g(ItemStack p_77657_1_) {
      String var2 = this.func_77667_c(p_77657_1_);
      return var2 == null?"":StatCollector.func_74838_a(var2);
   }

   public String func_77658_a() {
      return "item." + this.field_77774_bZ;
   }

   public String func_77667_c(ItemStack p_77667_1_) {
      return "item." + this.field_77774_bZ;
   }

   public Item func_77642_a(Item p_77642_1_) {
      this.field_77700_c = p_77642_1_;
      return this;
   }

   public boolean func_77630_h(ItemStack p_77630_1_) {
      return true;
   }

   public boolean func_77651_p() {
      return true;
   }

   public Item func_77668_q() {
      return this.field_77700_c;
   }

   public boolean func_77634_r() {
      return this.field_77700_c != null;
   }

   public String func_77635_s() {
      return StatCollector.func_74838_a(this.func_77658_a() + ".name");
   }

   public String func_77653_i(ItemStack p_77653_1_) {
      return StatCollector.func_74838_a(this.func_77667_c(p_77653_1_) + ".name");
   }

   @SideOnly(Side.CLIENT)
   public int func_82790_a(ItemStack p_82790_1_, int p_82790_2_) {
      return 16777215;
   }

   public void func_77663_a(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {}

   public void func_77622_d(ItemStack p_77622_1_, World p_77622_2_, EntityPlayer p_77622_3_) {}

   public boolean func_77643_m_() {
      return false;
   }

   public EnumAction func_77661_b(ItemStack p_77661_1_) {
      return EnumAction.none;
   }

   public int func_77626_a(ItemStack p_77626_1_) {
      return 0;
   }

   public void func_77615_a(ItemStack p_77615_1_, World p_77615_2_, EntityPlayer p_77615_3_, int p_77615_4_) {}

   public Item func_77631_c(String p_77631_1_) {
      this.field_77785_bY = p_77631_1_;
      return this;
   }

   public String func_77666_t() {
      return this.field_77785_bY;
   }

   public boolean func_77632_u() {
      return this.field_77785_bY != null;
   }

   @SideOnly(Side.CLIENT)
   public void func_77624_a(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {}

   public String func_77628_j(ItemStack p_77628_1_) {
      return ("" + StatCollector.func_74838_a(this.func_77657_g(p_77628_1_) + ".name")).trim();
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77636_d(ItemStack p_77636_1_) {
      return p_77636_1_.func_77948_v();
   }

   @SideOnly(Side.CLIENT)
   public EnumRarity func_77613_e(ItemStack p_77613_1_) {
      return p_77613_1_.func_77948_v()?EnumRarity.rare:EnumRarity.common;
   }

   public boolean func_77616_k(ItemStack p_77616_1_) {
      return this.func_77639_j() == 1 && this.func_77645_m();
   }

   protected MovingObjectPosition func_77621_a(World p_77621_1_, EntityPlayer p_77621_2_, boolean p_77621_3_) {
      float var4 = 1.0F;
      float var5 = p_77621_2_.field_70127_C + (p_77621_2_.field_70125_A - p_77621_2_.field_70127_C) * var4;
      float var6 = p_77621_2_.field_70126_B + (p_77621_2_.field_70177_z - p_77621_2_.field_70126_B) * var4;
      double var7 = p_77621_2_.field_70169_q + (p_77621_2_.field_70165_t - p_77621_2_.field_70169_q) * (double)var4;
      double var9 = p_77621_2_.field_70167_r + (p_77621_2_.field_70163_u - p_77621_2_.field_70167_r) * (double)var4 + 1.62D - (double)p_77621_2_.field_70129_M;
      double var11 = p_77621_2_.field_70166_s + (p_77621_2_.field_70161_v - p_77621_2_.field_70166_s) * (double)var4;
      Vec3 var13 = p_77621_1_.func_82732_R().func_72345_a(var7, var9, var11);
      float var14 = MathHelper.func_76134_b(-var6 * 0.017453292F - 3.1415927F);
      float var15 = MathHelper.func_76126_a(-var6 * 0.017453292F - 3.1415927F);
      float var16 = -MathHelper.func_76134_b(-var5 * 0.017453292F);
      float var17 = MathHelper.func_76126_a(-var5 * 0.017453292F);
      float var18 = var15 * var16;
      float var20 = var14 * var16;
      double var21 = 5.0D;
      Vec3 var23 = var13.func_72441_c((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
      return p_77621_1_.func_72831_a(var13, var23, p_77621_3_, !p_77621_3_);
   }

   public int func_77619_b() {
      return 0;
   }

   @SideOnly(Side.CLIENT)
   public boolean func_77623_v() {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public Icon func_77618_c(int p_77618_1_, int p_77618_2_) {
      return this.func_77617_a(p_77618_1_);
   }

   @SideOnly(Side.CLIENT)
   public void func_77633_a(int p_77633_1_, CreativeTabs p_77633_2_, List p_77633_3_) {
      p_77633_3_.add(new ItemStack(p_77633_1_, 1, 0));
   }

   public Item func_77637_a(CreativeTabs p_77637_1_) {
      this.field_77701_a = p_77637_1_;
      return this;
   }

   @SideOnly(Side.CLIENT)
   public CreativeTabs func_77640_w() {
      return this.field_77701_a;
   }

   public boolean func_82788_x() {
      return true;
   }

   public boolean func_82789_a(ItemStack p_82789_1_, ItemStack p_82789_2_) {
      return false;
   }

   @SideOnly(Side.CLIENT)
   public void func_94581_a(IconRegister p_94581_1_) {
      this.field_77791_bV = p_94581_1_.func_94245_a(this.func_111208_A());
   }

   public Multimap func_111205_h() {
      return HashMultimap.create();
   }

   public Item func_111206_d(String p_111206_1_) {
      this.field_111218_cA = p_111206_1_;
      return this;
   }

   @SideOnly(Side.CLIENT)
   protected String func_111208_A() {
      return this.field_111218_cA == null?"MISSING_ICON_ITEM_" + this.field_77779_bT + "_" + this.field_77774_bZ:this.field_111218_cA;
   }

   static {
      StatList.func_75925_c();
   }
}
