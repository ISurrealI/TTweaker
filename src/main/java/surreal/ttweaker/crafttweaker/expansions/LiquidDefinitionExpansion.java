package surreal.ttweaker.crafttweaker.expansions;

import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraftforge.fluids.FluidRegistry;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenExpansion("crafttweaker.liquid.ILiquidDefinition")
public class LiquidDefinitionExpansion {

    @ZenMethod
    public static boolean hasBucket(ILiquidDefinition liquid) {
        return FluidRegistry.hasBucket(CraftTweakerMC.getFluid(liquid));
    }

    @ZenMethod
    public static boolean addBucket(ILiquidDefinition liquid) {
        return FluidRegistry.addBucketForFluid(CraftTweakerMC.getFluid(liquid));
    }
}
