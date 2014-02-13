package femtocraft.managers.assembler;

import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.managers.research.TechLevel;
import femtocraft.managers.research.Technology;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.logging.Level;

public class FemtocraftAssemblerRecipe implements Comparable {
	public ItemStack[] input;
	public Integer mass;
	public ItemStack output;
	public TechLevel techLevel;
	public Technology tech;

	public FemtocraftAssemblerRecipe(ItemStack[] input, Integer mass,
			ItemStack output, TechLevel techLevel, Technology tech) {
		this.input = input;
		this.mass = mass;
		this.output = output;
		this.techLevel = techLevel;
		this.tech = tech;
	}

	private FemtocraftAssemblerRecipe() {
	}

    @Override
	public int compareTo(Object o) {
		FemtocraftAssemblerRecipe ir = (FemtocraftAssemblerRecipe) o;
		for (int i = 0; i < 9; i++) {
			int comp = FemtocraftUtils.compareItem(input[i], ir.input[i]);
			if (comp != 0)
				return comp;
		}

		if (mass < ir.mass)
			return -1;
		if (mass > ir.mass)
			return 1;

		int comp = FemtocraftUtils.compareItem(output, ir.output);
		if (comp != 0)
			return comp;

		return 0;
	}

	public void saveToNBTTagCompound(NBTTagCompound compound) {
		// Input
		NBTTagList inputList = new NBTTagList();
		for (int i = 0; i < input.length; ++i) {
			NBTTagCompound itemCompound = new NBTTagCompound();
			itemCompound.setByte("Slot", (byte) i);
			if (input[i] != null) {
				NBTTagCompound item = new NBTTagCompound();
				input[i].writeToNBT(item);
				itemCompound.setTag("item", item);
			}
			inputList.appendTag(itemCompound);
		}
		compound.setTag("input", inputList);

		// Mass
		compound.setInteger("mass", mass);

		// Output
		NBTTagCompound outputCompound = new NBTTagCompound();
		output.writeToNBT(outputCompound);

		compound.setCompoundTag("output", outputCompound);

		// TechLevel
		compound.setString("techLevel", techLevel.key);

		// Technology
		if (tech != null) {
			compound.setString("technology", tech.name);
		}
	}

	public static FemtocraftAssemblerRecipe loadFromNBTTagCompound(
			NBTTagCompound compound) {
		FemtocraftAssemblerRecipe recipe = new FemtocraftAssemblerRecipe();
		recipe.input = new ItemStack[9];

		// Input
		NBTTagList inputList = compound.getTagList("input");
		for (int i = 0; i < inputList.tagCount(); ++i) {
			NBTTagCompound itemCompound = (NBTTagCompound) inputList.tagAt(i);
			byte slot = itemCompound.getByte("Slot");
			if (slot != (byte) i) {
				Femtocraft.logger
						.log(Level.WARNING,
								"Slot mismatch occurred while loading AssemblerRecipe.");
			}
			if (itemCompound.hasKey("item")) {
				recipe.input[i] = ItemStack
						.loadItemStackFromNBT((NBTTagCompound) itemCompound
								.getTag("item"));
			}
		}

		// Mass
		recipe.mass = compound.getInteger("mass");

		// Output
		recipe.output = ItemStack
				.loadItemStackFromNBT((NBTTagCompound) compound
						.getTag("output"));

		// TechLevel
		recipe.techLevel = TechLevel.getTech(compound.getString("techLevel"));

		// Technology
		if (compound.hasKey("technology")) {
			recipe.tech = Femtocraft.researchManager.getTechnology(compound
					.getString("technology"));
		}

		return recipe;
	}
}
