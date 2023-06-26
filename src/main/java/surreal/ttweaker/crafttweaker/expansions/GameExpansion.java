package surreal.ttweaker.crafttweaker.expansions;

import crafttweaker.api.block.IBlockDefinition;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ZenExpansion("crafttweaker.game.IGame")
public class GameExpansion {

    @ZenMethodStatic
    public static IBlockDefinition getBlock(String name) {
        return CraftTweakerMC.getBlockDefinition(Block.getBlockFromName(name));
    }
}