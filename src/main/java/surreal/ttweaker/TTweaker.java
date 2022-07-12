package surreal.ttweaker;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = TTweaker.MODID, name = TTweaker.NAME, version = TTweaker.VERSION, dependencies = "required-after:forge@[14.23.5.2847,);required-after:crafttweaker")
public class TTweaker {
    public static final String MODID = "ttweaker";
    public static final String NAME = "TTweaker";
    public static final String VERSION = "1.2";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    static {
        FluidRegistry.enableUniversalBucket();
    }
}
