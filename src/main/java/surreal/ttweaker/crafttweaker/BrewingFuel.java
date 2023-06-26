package surreal.ttweaker.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.ttweaker.utils.ComparableStack;

import java.util.Map;

@ZenClass("mods.ttweaker.BrewingFuel")
public class BrewingFuel {

    private static final Map<ComparableStack, Integer> map = new Object2IntOpenHashMap<>();

    @ZenMethod
    public static void addFuel(IItemStack stack) {
        addFuel(stack, 20);
    }

    @ZenMethod
    public static void addFuel(IItemStack stack, int fuelAmount) {

        if (stack == null) {
            CraftTweakerAPI.logError("[TTweaker - Brewing Fuel]: Stack is null!");
            return;
        }

        if (fuelAmount <= 0) {
            CraftTweakerAPI.logError("[TTweaker - Brewing Fuel]: Amount is smaller or equal to 0!");
            return;
        }

        map.put(new ComparableStack(CraftTweakerMC.getItemStack(stack)), fuelAmount);
    }

    @ZenMethod
    public static void clear() {
        map.clear();
    }

    // INTERNAL //
    public static int getTime(ItemStack stack) {
        LogManager.getLogger("getTime").info("Testing with " + stack.getItem().getRegistryName());
        return map.get(new ComparableStack(stack));
    }

    public static boolean hasKey(ItemStack stack) {
        ComparableStack c = new ComparableStack(stack);
        LogManager.getLogger("hasKey").info("Testing with " + c);
        return map.containsKey(new ComparableStack(stack));
    }


    static {
        map.put(new ComparableStack(Items.BLAZE_POWDER), 20);
    }

}
