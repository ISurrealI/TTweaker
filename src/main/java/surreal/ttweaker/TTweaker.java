package surreal.ttweaker;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import surreal.ttweaker.core.TTweakerHooks;

@Mod(modid = TTweaker.MODID, name = TTweaker.NAME, version = TTweaker.VERSION)
public class TTweaker {
    public static final String MODID = "ttweaker";
    public static final String NAME = "TTweaker";
    public static final String VERSION = "1.0";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public boolean isValid(int index, ItemStack stack) {
        if (index == 4) {
            return TTweakerHooks.hasFuel(stack);
        } else return BrewingRecipeRegistry.isValidInput(stack);
    }
}
