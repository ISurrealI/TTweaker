package surreal.ttweaker.utils;

import it.unimi.dsi.fastutil.Hash;
import net.minecraft.item.ItemStack;

public class HashStrategies {

    public static final Hash.Strategy<ItemStack> ITEMSTACK_STRATEGY = new Hash.Strategy<>() {

        @Override
        public int hashCode(ItemStack stack) {
            int i = stack.getItem().hashCode() << 13;
            i |= (stack.getMetadata() + 1) << 31;

            if (stack.hasTagCompound()) {
                i |= stack.getTagCompound().toString().hashCode() << 17;
            }

            return i;
        }

        @Override
        public boolean equals(ItemStack a, ItemStack b) {
            if (a == null || b == null) return false;
            return a.getItem() == b.getItem() && (a.getMetadata() == b.getMetadata() || b.getMetadata() == Short.MAX_VALUE) && ((a.hasTagCompound() == b.hasTagCompound()) && (a.getTagCompound() == b.getTagCompound()));
        }
    };
}
