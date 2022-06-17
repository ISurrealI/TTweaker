package surreal.ttweaker;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = TTweaker.MODID, name = TTweaker.NAME, version = TTweaker.VERSION)
public class TTweaker {
    public static final String MODID = "ttweaker";
    public static final String NAME = "TTweaker";
    public static final String VERSION = "1.0";

    static {
        FluidRegistry.enableUniversalBucket();
    }
}
