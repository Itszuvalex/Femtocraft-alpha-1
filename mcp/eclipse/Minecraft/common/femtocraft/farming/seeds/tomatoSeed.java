package femtocraft.farming.seeds;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;



public class tomatoSeed extends Item{
	public tomatoSeed(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
		func_111206_d(Femtocraft.ID.toLowerCase() +":" + "tomatoSeed");
	}
	
	public void updateIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "tomatoSeed");
    }
}
