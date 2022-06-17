package surreal.ttweaker.expansions;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@SuppressWarnings("unused")

@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class WorldExpansion {
    @ZenMethod
    public static void setBlockToAir(IWorld world, IBlockPos pos) {
        ((World) world.getInternal()).setBlockToAir(((BlockPos) pos.getInternal()));
    }

    @ZenMethod
    public static IEntity[] getLoadedEntities(IWorld world) {
        List<Entity> loadedEntities = ((World) world.getInternal()).getLoadedEntityList();
        IEntity[] ret = new IEntity[loadedEntities.size()];
        for (int i = 0; i < loadedEntities.size(); i++) {
            ret[i] = CraftTweakerMC.getIEntity(loadedEntities.get(i));
        }
        return ret;
    }
}
