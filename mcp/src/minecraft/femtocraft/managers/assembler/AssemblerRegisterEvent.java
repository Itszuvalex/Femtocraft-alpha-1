package femtocraft.managers.assembler;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class AssemblerRegisterEvent extends Event {

	public final FemtocraftAssemblerRecipe recipe;
	public AssemblerRegisterEvent(FemtocraftAssemblerRecipe recipe) {
		this.recipe = recipe;
	}
	@Cancelable
	public static class AssemblerDecompositionRegisterEvent extends AssemblerRegisterEvent
	{
		public AssemblerDecompositionRegisterEvent(
				FemtocraftAssemblerRecipe recipe) {
			super(recipe);
		}
	}
	@Cancelable
	public static class AssemblerRecompositionRegisterEvent extends AssemblerRegisterEvent
	{
		public AssemblerRecompositionRegisterEvent(
				FemtocraftAssemblerRecipe recipe) {
			super(recipe);
		}
	}
}
