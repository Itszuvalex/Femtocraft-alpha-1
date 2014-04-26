package femtocraft.core.ore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;

import java.util.Random;

public class BlockOreFarenite extends BlockOre {

    public BlockOreFarenite(int id) {
        super(id);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "BlockOreFarenite");
        setUnlocalizedName("BlockOreFarenite");
        setHardness(3.0f);
        setStepSound(Block.soundStoneFootstep);
        setResistance(1f);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "BlockOreFarenite");
    }

    public int idDropped(int par1, Random random, int par2) {
        return Femtocraft.ingotFarenite.itemID;
    }

    public int quantityDropped(Random random) {
        return 4;
    }
}
