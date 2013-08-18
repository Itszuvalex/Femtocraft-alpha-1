package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.Language;

@SideOnly(Side.CLIENT)
class GuiSlotLanguage extends GuiSlot {

   private final List field_77251_g;
   private final Map field_77253_h;
   // $FF: synthetic field
   final GuiLanguage field_77252_a;


   public GuiSlotLanguage(GuiLanguage p_i1042_1_) {
      super(p_i1042_1_.field_73882_e, p_i1042_1_.field_73880_f, p_i1042_1_.field_73881_g, 32, p_i1042_1_.field_73881_g - 65 + 4, 18);
      this.field_77252_a = p_i1042_1_;
      this.field_77251_g = Lists.newArrayList();
      this.field_77253_h = Maps.newHashMap();
      Iterator var2 = GuiLanguage.func_135011_a(p_i1042_1_).func_135040_d().iterator();

      while(var2.hasNext()) {
         Language var3 = (Language)var2.next();
         this.field_77253_h.put(var3.func_135034_a(), var3);
         this.field_77251_g.add(var3.func_135034_a());
      }

   }

   protected int func_77217_a() {
      return this.field_77251_g.size();
   }

   protected void func_77213_a(int p_77213_1_, boolean p_77213_2_) {
      Language var3 = (Language)this.field_77253_h.get(this.field_77251_g.get(p_77213_1_));
      GuiLanguage.func_135011_a(this.field_77252_a).func_135045_a(var3);
      GuiLanguage.func_74043_a(this.field_77252_a).field_74363_ab = var3.func_135034_a();
      this.field_77252_a.field_73882_e.func_110436_a();
      this.field_77252_a.field_73886_k.func_78264_a(GuiLanguage.func_135011_a(this.field_77252_a).func_135042_a());
      this.field_77252_a.field_73886_k.func_78275_b(GuiLanguage.func_135011_a(this.field_77252_a).func_135044_b());
      GuiLanguage.func_74042_b(this.field_77252_a).field_73744_e = I18n.func_135053_a("gui.done");
      GuiLanguage.func_74043_a(this.field_77252_a).func_74303_b();
   }

   protected boolean func_77218_a(int p_77218_1_) {
      return ((String)this.field_77251_g.get(p_77218_1_)).equals(GuiLanguage.func_135011_a(this.field_77252_a).func_135041_c().func_135034_a());
   }

   protected int func_77212_b() {
      return this.func_77217_a() * 18;
   }

   protected void func_77221_c() {
      this.field_77252_a.func_73873_v_();
   }

   protected void func_77214_a(int p_77214_1_, int p_77214_2_, int p_77214_3_, int p_77214_4_, Tessellator p_77214_5_) {
      this.field_77252_a.field_73886_k.func_78275_b(true);
      this.field_77252_a.func_73732_a(this.field_77252_a.field_73886_k, ((Language)this.field_77253_h.get(this.field_77251_g.get(p_77214_1_))).toString(), this.field_77252_a.field_73880_f / 2, p_77214_3_ + 1, 16777215);
      this.field_77252_a.field_73886_k.func_78275_b(GuiLanguage.func_135011_a(this.field_77252_a).func_135041_c().func_135035_b());
   }
}
