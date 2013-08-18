package net.minecraft.server.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.Vector;
import javax.swing.JList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;

@SideOnly(Side.SERVER)
public class PlayerListComponent extends JList implements IUpdatePlayerListBox {

   private MinecraftServer field_120015_a;
   private int field_120014_b;


   public PlayerListComponent(MinecraftServer p_i2366_1_) {
      this.field_120015_a = p_i2366_1_;
      p_i2366_1_.func_82010_a(this);
   }

   public void func_73660_a() {
      if(this.field_120014_b++ % 20 == 0) {
         Vector var1 = new Vector();

         for(int var2 = 0; var2 < this.field_120015_a.func_71203_ab().field_72404_b.size(); ++var2) {
            var1.add(((EntityPlayerMP)this.field_120015_a.func_71203_ab().field_72404_b.get(var2)).func_70005_c_());
         }

         this.setListData(var1);
      }

   }
}
