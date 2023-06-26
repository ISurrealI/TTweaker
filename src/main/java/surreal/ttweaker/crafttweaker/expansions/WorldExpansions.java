package surreal.ttweaker.crafttweaker.expansions;

import crafttweaker.api.entity.IEntity;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import crafttweaker.api.world.IWorld;
import net.minecraft.entity.Entity;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenExpansion("crafttweaker.world.IWorld")
public class WorldExpansions {

    @ZenMethod
    public static void setBlockToAir(IWorld world, IBlockPos pos) {
        CraftTweakerMC.getWorld(world).setBlockToAir(CraftTweakerMC.getBlockPos(pos));
    }

    @ZenMethod
    public static IEntity[] getLoadedEntities(IWorld world) {
        List<Entity> loadedEntities = CraftTweakerMC.getWorld(world).getLoadedEntityList();
        IEntity[] ret = new IEntity[loadedEntities.size()];

        for(int i = 0; i < loadedEntities.size(); ++i) {
            ret[i] = CraftTweakerMC.getIEntity(loadedEntities.get(i));
        }

        return ret;
    }
}
