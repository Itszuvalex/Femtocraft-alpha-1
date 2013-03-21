package femtocraft.farming.produce;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import femtocraft.Femtocraft;

public class Tomato extends Item{
	public Tomato(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
	}
	
	public void func_94581_a(IconRegister iconRegister)
	{
		this.iconIndex = iconRegister.func_94245_a("Femtocraft:tomato");
	}

}
