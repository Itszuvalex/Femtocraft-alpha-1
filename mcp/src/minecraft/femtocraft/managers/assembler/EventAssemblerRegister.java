package femtocraft.managers.assembler;

import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class EventAssemblerRegister extends Event {

	public final AssemblerRecipe recipe;

	public EventAssemblerRegister(AssemblerRecipe recipe) {
		this.recipe = recipe;
	}

	@Cancelable
	public static class AssemblerDecompositionRegisterEvent extends
            EventAssemblerRegister {
		public AssemblerDecompositionRegisterEvent(
				AssemblerRecipe recipe) {
			super(recipe);
		}
	}

	@Cancelable
	public static class AssemblerRecompositionRegisterEvent extends
            EventAssemblerRegister {
		public AssemblerRecompositionRegisterEvent(
				AssemblerRecipe recipe) {
			super(recipe);
		}
	}
}
