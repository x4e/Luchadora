package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class WorldProviderWrapper
{
	private final Minecraft mc;
	
	public WorldProviderWrapper(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public float[] getLightBrightnessTable()
	{
		return mc.world.provider.getLightBrightnessTable();
	}
	
	public void generateLightBrightnessTable()
	{
		try
		{
			getGenerateLightmapMethod().invoke(mc.world.provider);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Couldnt generate lightmap", e);
		}
	}
	
	private static Method lightmapMethod = null;
	private static Method getGenerateLightmapMethod()
	{
		if (lightmapMethod == null)
			lightmapMethod =
				Objects.requireNonNull(
					ObfuscationReflectionHelper.findMethod(
						WorldProvider.class,
						"func_76556_a",
						void.class
					)
				);
		
		return lightmapMethod;
	}
}
