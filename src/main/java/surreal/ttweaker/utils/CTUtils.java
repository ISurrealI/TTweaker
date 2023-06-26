package surreal.ttweaker.utils;

import crafttweaker.api.item.IItemDefinition;
import crafttweaker.mc1120.item.MCItemDefinition;
import net.minecraft.item.Item;

public class CTUtils {

    public static IItemDefinition getItem(Item item) {
        return new MCItemDefinition(item.getRegistryName().toString(), item);
    }
}
