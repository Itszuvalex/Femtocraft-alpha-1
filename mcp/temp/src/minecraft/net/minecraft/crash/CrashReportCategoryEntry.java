package net.minecraft.crash;


class CrashReportCategoryEntry {

   private final String field_85092_a;
   private final String field_85091_b;


   public CrashReportCategoryEntry(String p_i1352_1_, Object p_i1352_2_) {
      this.field_85092_a = p_i1352_1_;
      if(p_i1352_2_ == null) {
         this.field_85091_b = "~~NULL~~";
      } else if(p_i1352_2_ instanceof Throwable) {
         Throwable var3 = (Throwable)p_i1352_2_;
         this.field_85091_b = "~~ERROR~~ " + var3.getClass().getSimpleName() + ": " + var3.getMessage();
      } else {
         this.field_85091_b = p_i1352_2_.toString();
      }

   }

   public String func_85089_a() {
      return this.field_85092_a;
   }

   public String func_85090_b() {
      return this.field_85091_b;
   }
}
