package surreal.ttweaker;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import surreal.ttweaker.expansions.*;
import surreal.ttweaker.integration.psi.CTTrick;

@Mod(modid = TTweaker.MODID, name = TTweaker.NAME, version = TTweaker.VERSION)
public class TTweaker {
    public static final String MODID = "ttweaker";
    public static final String NAME = "TTweaker";
    public static final String VERSION = "1.0";

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        loadClass(TTConfig.ADDITIONS.gameExpansion, GameExpansion.class);
        loadClass(TTConfig.ADDITIONS.liquidDefExpansion, LiquidDefinitionExpansion.class);
        loadClass(TTConfig.ADDITIONS.liquidStackExpansion, LiquidStackExpansion.class);

        loadClass(TTConfig.SUPPORTS.psiSupport, CTTrick.class);
    }

    private static void loadClass(boolean check, Class<?>... clazz) {
        if (check) {
            for (Class<?> c : clazz) {
                CraftTweakerAPI.registerClass(c);
            }
        }
    }
}
