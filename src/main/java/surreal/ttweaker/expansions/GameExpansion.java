package surreal.ttweaker.expansions;

import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.liquid.ILiquidDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.FluidRegistry;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@SuppressWarnings("unused")
@ZenExpansion("crafttweaker.game.IGame")
public class GameExpansion {
    @ZenMethodStatic
    public static ILiquidDefinition getLiquid(String name) {
        return CraftTweakerMC.getILiquidDefinition(FluidRegistry.getFluid(name));
    }

    @ZenMethodStatic
    public static IBlockDefinition getBlock(String name) {
        return CraftTweakerMC.getBlockDefinition(Block.getBlockFromName(name));
    }
}
