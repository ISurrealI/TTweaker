package surreal.ttweaker.core;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import surreal.TTUtils;
import surreal.ttweaker.TTweaker;
import surreal.ttweaker.crafttweaker.CTBrewingFuel;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class TTweakerHooks {
    public static boolean hasFuel(ItemStack stack) {
        if (!stack.isEmpty()) {
            String amongus = TTUtils.stackAsString(stack);
            return CTBrewingFuel.FUELS.containsKey(amongus);
        }

        return false;
    }

    public static Fuel getFuelSlot(IInventory inventory, int index, int xPosition, int yPosition) {
        return new Fuel(inventory, index, xPosition, yPosition);
    }

    public static int getFuelValue(ItemStack stack) {
        String a = TTUtils.stackAsString(stack);
        return CTBrewingFuel.FUELS.getOrDefault(a, 0);
    }

    public static class Fuel extends Slot {
        public Fuel(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return isValidBrewingFuel(stack);
        }

        public static boolean isValidBrewingFuel(ItemStack itemStackIn) {
            return TTweakerHooks.hasFuel(itemStackIn);
        }
    }
}