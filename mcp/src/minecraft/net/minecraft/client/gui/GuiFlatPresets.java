package net.minecraft.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.FlatLayerInfo;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class GuiFlatPresets extends GuiScreen
{
    /** RenderItem instance used to render preset icons. */
    private static RenderItem presetIconRenderer = new RenderItem();

    /** List of defined flat world presets. */
    private static final List presets = new ArrayList();
    private final GuiCreateFlatWorld createFlatWorldGui;
    private String field_82300_d;
    private String field_82308_m;
    private String field_82306_n;
    private GuiFlatPresetsListSlot theFlatPresetsListSlot;
    private GuiButton theButton;
    private GuiTextField theTextField;

    public GuiFlatPresets(GuiCreateFlatWorld par1GuiCreateFlatWorld)
    {
        this.createFlatWorldGui = par1GuiCreateFlatWorld;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.field_82300_d = I18n.getString("createWorld.customize.presets.title");
        this.field_82308_m = I18n.getString("createWorld.customize.presets.share");
        this.field_82306_n = I18n.getString("createWorld.customize.presets.list");
        this.theTextField = new GuiTextField(this.fontRenderer, 50, 40, this.width - 100, 20);
        this.theFlatPresetsListSlot = new GuiFlatPresetsListSlot(this);
        this.theTextField.setMaxStringLength(1230);
        this.theTextField.setText(this.createFlatWorldGui.getFlatGeneratorInfo());
        this.buttonList.add(this.theButton = new GuiButton(0, this.width / 2 - 155, this.height - 28, 150, 20, I18n.getString("createWorld.customize.presets.select")));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height - 28, 150, 20, I18n.getString("gui.cancel")));
        this.func_82296_g();
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int par1, int par2, int par3)
    {
        this.theTextField.mouseClicked(par1, par2, par3);
        super.mouseClicked(par1, par2, par3);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char par1, int par2)
    {
        if (!this.theTextField.textboxKeyTyped(par1, par2))
        {
            super.keyTyped(par1, par2);
        }
    }

    /**
     * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
     */
    protected void actionPerformed(GuiButton par1GuiButton)
    {
        if (par1GuiButton.id == 0 && this.func_82293_j())
        {
            this.createFlatWorldGui.setFlatGeneratorInfo(this.theTextField.getText());
            this.mc.displayGuiScreen(this.createFlatWorldGui);
        }
        else if (par1GuiButton.id == 1)
        {
            this.mc.displayGuiScreen(this.createFlatWorldGui);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int par1, int par2, float par3)
    {
        this.drawDefaultBackground();
        this.theFlatPresetsListSlot.drawScreen(par1, par2, par3);
        this.drawCenteredString(this.fontRenderer, this.field_82300_d, this.width / 2, 8, 16777215);
        this.drawString(this.fontRenderer, this.field_82308_m, 50, 30, 10526880);
        this.drawString(this.fontRenderer, this.field_82306_n, 50, 70, 10526880);
        this.theTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.theTextField.updateCursorCounter();
        super.updateScreen();
    }

    public void func_82296_g()
    {
        boolean flag = this.func_82293_j();
        this.theButton.enabled = flag;
    }

    private boolean func_82293_j()
    {
        return this.theFlatPresetsListSlot.field_82459_a > -1 && this.theFlatPresetsListSlot.field_82459_a < presets.size() || this.theTextField.getText().length() > 1;
    }

    /**
     * Add a flat world preset with no world features.
     */
    public static void addPresetNoFeatures(String par0Str, int par1, BiomeGenBase par2BiomeGenBase, FlatLayerInfo ... par3ArrayOfFlatLayerInfo)
    {
        addPreset(par0Str, par1, par2BiomeGenBase, (List)null, par3ArrayOfFlatLayerInfo);
    }

    /**
     * Add a flat world preset.
     */
    public static void addPreset(String par0Str, int par1, BiomeGenBase par2BiomeGenBase, List par3List, FlatLayerInfo ... par4ArrayOfFlatLayerInfo)
    {
        FlatGeneratorInfo flatgeneratorinfo = new FlatGeneratorInfo();

        for (int j = par4ArrayOfFlatLayerInfo.length - 1; j >= 0; --j)
        {
            flatgeneratorinfo.getFlatLayers().add(par4ArrayOfFlatLayerInfo[j]);
        }

        flatgeneratorinfo.setBiome(par2BiomeGenBase.biomeID);
        flatgeneratorinfo.func_82645_d();

        if (par3List != null)
        {
            Iterator iterator = par3List.iterator();

            while (iterator.hasNext())
            {
                String s1 = (String)iterator.next();
                flatgeneratorinfo.getWorldFeatures().put(s1, new HashMap());
            }
        }

        presets.add(new GuiFlatPresetsItem(par1, par0Str, flatgeneratorinfo.toString()));
    }

    /**
     * Return the RenderItem instance used to render preset icons.
     */
    static RenderItem getPresetIconRenderer()
    {
        return presetIconRenderer;
    }

    /**
     * Return the list of defined flat world presets.
     */
    static List getPresets()
    {
        return presets;
    }

    static GuiFlatPresetsListSlot func_82292_a(GuiFlatPresets par0GuiFlatPresets)
    {
        return par0GuiFlatPresets.theFlatPresetsListSlot;
    }

    static GuiTextField func_82298_b(GuiFlatPresets par0GuiFlatPresets)
    {
        return par0GuiFlatPresets.theTextField;
    }

    static
    {
        addPreset("Classic Flat", Block.grass.blockID, BiomeGenBase.plains, Arrays.asList(new String[] {"village"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(2, Block.dirt.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
        addPreset("Tunnelers\' Dream", Block.stone.blockID, BiomeGenBase.extremeHills, Arrays.asList(new String[] {"biome_1", "dungeon", "decoration", "stronghold", "mineshaft"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(230, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
        addPreset("Water World", Block.waterMoving.blockID, BiomeGenBase.plains, Arrays.asList(new String[] {"village", "biome_1"}), new FlatLayerInfo[] {new FlatLayerInfo(90, Block.waterStill.blockID), new FlatLayerInfo(5, Block.sand.blockID), new FlatLayerInfo(5, Block.dirt.blockID), new FlatLayerInfo(5, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
        addPreset("Overworld", Block.tallGrass.blockID, BiomeGenBase.plains, Arrays.asList(new String[] {"village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon", "lake", "lava_lake"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
        addPreset("Snowy Kingdom", Block.snow.blockID, BiomeGenBase.icePlains, Arrays.asList(new String[] {"village", "biome_1"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.snow.blockID), new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(59, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
        addPreset("Bottomless Pit", Item.feather.itemID, BiomeGenBase.plains, Arrays.asList(new String[] {"village", "biome_1"}), new FlatLayerInfo[] {new FlatLayerInfo(1, Block.grass.blockID), new FlatLayerInfo(3, Block.dirt.blockID), new FlatLayerInfo(2, Block.cobblestone.blockID)});
        addPreset("Desert", Block.sand.blockID, BiomeGenBase.desert, Arrays.asList(new String[] {"village", "biome_1", "decoration", "stronghold", "mineshaft", "dungeon"}), new FlatLayerInfo[] {new FlatLayerInfo(8, Block.sand.blockID), new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
        addPresetNoFeatures("Redstone Ready", Item.redstone.itemID, BiomeGenBase.desert, new FlatLayerInfo[] {new FlatLayerInfo(52, Block.sandStone.blockID), new FlatLayerInfo(3, Block.stone.blockID), new FlatLayerInfo(1, Block.bedrock.blockID)});
    }
}
