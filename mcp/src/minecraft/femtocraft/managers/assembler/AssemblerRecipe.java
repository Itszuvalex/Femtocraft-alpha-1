package femtocraft.managers.assembler;

import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.logging.Level;

public class AssemblerRecipe implements Comparable {
	public ItemStack[] input;
	public Integer mass;
	public ItemStack output;
	public EnumTechLevel enumTechLevel;
	public ResearchTechnology tech;

	public AssemblerRecipe(ItemStack[] input, Integer mass,
                           ItemStack output, EnumTechLevel enumTechLevel, ResearchTechnology tech) {
		this.input = input;
		this.mass = mass;
		this.output = output;
		this.enumTechLevel = enumTechLevel;
		this.tech = tech;
	}

	private AssemblerRecipe() {
	}

    @Override
	public int compareTo(Object o) {
		AssemblerRecipe ir = (AssemblerRecipe) o;
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

		// FluidMass
		compound.setInteger("mass", mass);

		// Output
		NBTTagCompound outputCompound = new NBTTagCompound();
		output.writeToNBT(outputCompound);

		compound.setCompoundTag("output", outputCompound);

		// EnumTechLevel
		compound.setString("enumTechLevel", enumTechLevel.key);

		// ResearchTechnology
		if (tech != null) {
			compound.setString("researchTechnology", tech.name);
		}
	}

	public static AssemblerRecipe loadFromNBTTagCompound(
			NBTTagCompound compound) {
		AssemblerRecipe recipe = new AssemblerRecipe();
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

		// FluidMass
		recipe.mass = compound.getInteger("mass");

		// Output
		recipe.output = ItemStack
				.loadItemStackFromNBT((NBTTagCompound) compound
						.getTag("output"));

		// EnumTechLevel
		recipe.enumTechLevel = EnumTechLevel.getTech(compound.getString("enumTechLevel"));

		// ResearchTechnology
		if (compound.hasKey("researchTechnology")) {
			recipe.tech = Femtocraft.researchManager.getTechnology(compound
					.getString("researchTechnology"));
		}

		return recipe;
	}
}
