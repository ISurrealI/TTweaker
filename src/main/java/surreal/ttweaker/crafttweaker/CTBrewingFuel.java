package surreal.ttweaker.crafttweaker;

import com.google.common.collect.Maps;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.ttweaker.TTUtils;

import java.util.Map;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("mods.ttweaker.BrewingFuel")
public class CTBrewingFuel {
    public static final Map<String, Integer> FUELS = Maps.newHashMap();

    @ZenMethod
    public static void addFuel(IItemStack stack) {
        addFuel(stack, 20);
    }

    @ZenMethod
    public static void addFuel(IItemStack stack, int fuelAmount) {
        if (stack != null) FUELS.put(TTUtils.stackAsString((ItemStack) stack.getInternal()), Math.min(fuelAmount, 20));
    }

    @ZenMethod
    public static void clear() {
        FUELS.clear();
    }

    static {
        FUELS.put("minecraft:blaze_powder:0", 20);
    }
}
