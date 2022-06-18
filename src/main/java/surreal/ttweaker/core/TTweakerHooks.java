package surreal.ttweaker.core;

import net.minecraft.item.ItemStack;
import surreal.TTUtils;
import surreal.ttweaker.TTweaker;
import surreal.ttweaker.crafttweaker.CTBrewingFuel;

@SuppressWarnings("unused")
public class TTweakerHooks {
    public static boolean hasFuel(ItemStack stack) {
        if (!stack.isEmpty()) {
            String amongus = TTUtils.stackAsString(stack);
            TTweaker.LOGGER.info(amongus);
            return CTBrewingFuel.FUELS.contains(amongus);
        }

        return false;
    }
}