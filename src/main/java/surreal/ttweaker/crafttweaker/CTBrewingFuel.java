package surreal.ttweaker.crafttweaker;

import com.google.common.collect.Sets;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.TTUtils;

import java.util.Set;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("mods.ttweaker.BrewingFuel")
public class CTBrewingFuel {
    public static final Set<String> FUELS = Sets.newHashSet(TTUtils.stackAsString(new ItemStack(Items.BLAZE_POWDER)));

    @ZenMethod
    public static void addFuel(IItemStack stack) {
        if (stack != null) FUELS.add(TTUtils.stackAsString((ItemStack) stack.getInternal()));
    }

    @ZenMethod
    public static void clear() {
        FUELS.clear();
    }
}
