package surreal.ttweaker.core;

import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name(TTweakerLoadingPlugin.ID)
@IFMLLoadingPlugin.SortingIndex(1552)
@IFMLLoadingPlugin.MCVersion(ForgeVersion.mcVersion)
public class TTweakerLoadingPlugin implements IFMLLoadingPlugin {
    protected static final String ID = "Turquoise Tweaker";
    public static final Logger LOGGER = LogManager.getLogger(ID);
    public static final boolean deobf = FMLLaunchHandler.isDeobfuscatedEnvironment();

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { TTweakerClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) { }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
