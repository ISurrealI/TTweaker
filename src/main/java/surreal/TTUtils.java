package surreal;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TTUtils {
    public static String stackAsString(ItemStack stack) {
        String name = Item.REGISTRY.getNameForObject(stack.getItem()).toString();
        int meta = stack.getMetadata();

        NBTTagCompound nbt = stack.hasTagCompound() ? stack.getTagCompound().copy() : null;

        StringBuilder builder = new StringBuilder(name);
        builder.append(":").append(meta);

        if (nbt != null) builder.append("#").append(nbt);

        return builder.toString();
    }
}
