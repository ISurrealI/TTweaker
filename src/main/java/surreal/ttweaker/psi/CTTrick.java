package surreal.ttweaker.psi;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import vazkii.psi.api.PsiAPI;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("mods.psi.Trick")
@ModOnly("psi")
public class CTTrick {
    @ZenMethod
    public static void addRecipe(String trick, IIngredient input, IItemStack output, IItemStack minAssembly) {
        PsiAPI.registerTrickRecipe(trick, CraftTweakerMC.getIngredient(input), CraftTweakerMC.getItemStack(output), CraftTweakerMC.getItemStack(minAssembly));
    }

    @ZenMethod
    public static void removeAll() {
        PsiAPI.trickRecipes.clear();
    }

    @ZenMethod
    public static void remove(IItemStack output) {
        PsiAPI.trickRecipes.removeIf(recipe -> recipe.getOutput().isItemEqual(CraftTweakerMC.getItemStack(output)));
    }
}
