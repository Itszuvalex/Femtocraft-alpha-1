package femtocraft.industry.items;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.api.IAssemblerSchematic;
import femtocraft.managers.FemtocraftAssemblerRecipe;
import femtocraft.research.Technology;

public class AssemblySchematic extends Item implements IAssemblerSchematic {
	
	public AssemblySchematic(int itemID) {
		super(itemID);
		setCreativeTab(Femtocraft.femtocraftTab);
		setMaxStackSize(64);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		//This is called when displaying the tooltip for an item.  par4 is whether it's extended or not, I believe.
		NBTTagCompound itemCompound = par1ItemStack.stackTagCompound;
		if(itemCompound == null) return;
		if(!itemCompound.hasKey("recipe")) return;
		FemtocraftAssemblerRecipe recipe = FemtocraftAssemblerRecipe.loadFromNBTTagCompound((NBTTagCompound)itemCompound.getTag("recipe"));
		
		int uses = usesRemaining(par1ItemStack);
		String useString;
		if(uses == -1)
		{
			useString = "Infinite Uses";
		}
		else
		{
			useString = String.valueOf(uses);
		}
		String useLine = String.format("%s %s", "Uses Remaining:", useString);
		par3List.add(useLine);
		
		String outputLine = String.format("Output: %dx%s", recipe.output.stackSize, recipe.output.getDisplayName());
		par3List.add(outputLine);
		
		//End short
		if(!par4) return;
		//Begin long
		par3List.add("");
		
		for(int i = 0; i < 9; ++i)
		{
			ItemStack item = recipe.input[i];
			String inputString;
			if(item == null)
			{
				inputString = "empty";
			}
			else
			{
				inputString = String.format("%dx%s", item.stackSize, item.getDisplayName());
			}
			String inputLine = String.format("Input %d: %s", i, inputString);
			par3List.add(inputLine);
		}
		
		par3List.add("");
		
		String massLine = String.format("Mass: %d", recipe.mass);
		par3List.add(massLine);
		
		String techLevelLine = String.format("TechLevel: %s", recipe.techLevel.key);
		par3List.add(techLevelLine);
		
		Technology tech = recipe.tech;
		String techString;
		if(tech == null)
		{
			techString = "none";
		}
		else
		{
			techString = tech.name;
		}
		
		String techLine = String.format("Technology Required: %s", techString);
		par3List.add(techLine);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		return stack.stackTagCompound.hasNoTags() ? this.maxStackSize : 1;
	}

	@Override
	public boolean getShareTag() {
		return true;
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}

	@Override
	public boolean isRepairable() {
		return false;
	}

	@Override
	public FemtocraftAssemblerRecipe getRecipe(ItemStack stack) {
		return getRecipeFromNBT(stack.stackTagCompound);
	}
	

	@Override
	public boolean setRecipe(ItemStack stack, FemtocraftAssemblerRecipe recipe) {
		return addRecipeToNBT(stack, recipe);
	}

	@Override
	public boolean onAssemble(ItemStack stack) {
		if(usesRemaining(stack) == -1) return true;
		return !stack.attemptDamageItem(1, new Random());
	}

	private FemtocraftAssemblerRecipe getRecipeFromNBT(NBTTagCompound compound)
	{
		if(!compound.hasKey("recipe")) return null;
		return FemtocraftAssemblerRecipe.loadFromNBTTagCompound((NBTTagCompound) compound.getTag("recipe"));
	}
	
	private boolean addRecipeToNBT(ItemStack stack, FemtocraftAssemblerRecipe recipe)
	{
		if(stack.stackTagCompound == null)
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
		else if(stack.stackTagCompound.hasKey("recipe")) 
		{
			return false;
		}
		
		NBTTagCompound recipeCompound = new NBTTagCompound();
		recipe.saveToNBTTagCompound(recipeCompound);
		stack.stackTagCompound.setTag("recipe", recipeCompound);
		
		return true;
	}

	@Override
	public int usesRemaining(ItemStack stack) {
		return stack.getMaxDamage() - stack.getItemDamage();
	}

	@Override
	public ItemStack resultOfBreakdown(ItemStack stack) {
		return new ItemStack(this, 1);
	}
}
