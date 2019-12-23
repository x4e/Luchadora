package cookiedragon.luchadora.mixin;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class CoreMod implements IFMLLoadingPlugin
{
	public CoreMod()
	{
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.luchadora.json");
		MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
	}
	
	@Override
	public String[] getASMTransformerClass()
	{
		return new String[0];
	}
	
	@Override
	public String getModContainerClass()
	{
		return null;
	}
	
	@Nullable
	@Override
	public String getSetupClass()
	{
		return null;
	}
	
	@Override
	public void injectData(Map<String, Object> data)
	{
	
	}
	
	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}
}
