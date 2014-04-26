package femtocraft.models;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;

@SideOnly(Side.CLIENT)
public class ModelOrbitalEqualizer extends ModelBase {
    private IModelCustom test;

    public ModelOrbitalEqualizer() {
        test = AdvancedModelLoader.loadModel("/assets/femtocraft/models/orbitalEqualizer.obj");
    }

    public void render() {
        test.renderAll();
    }
}
