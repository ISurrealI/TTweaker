package surreal.ttweaker;

import net.minecraftforge.common.config.Config;

@Config(modid = TTweaker.MODID)
public class TTConfig {
    public static Additions ADDITIONS = new Additions();
    public static Supports SUPPORTS = new Supports();

    public static class Additions {
        @Config.Comment("IGame Expansion")
        public boolean gameExpansion = true;

        @Config.Comment("ILiquidDefinition Expansion")
        public boolean liquidDefExpansion = true;

        @Config.Comment("ILiquidStack Expansion")
        public boolean liquidStackExpansion = true;
    }

    public static class Supports {
        @Config.Comment("Psi Support")
        public boolean psiSupport = true;
    }
}
