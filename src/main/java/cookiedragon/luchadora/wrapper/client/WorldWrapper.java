package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class WorldWrapper
{
	private final Minecraft mc;
	public final WorldProviderWrapper provider;
	
	public WorldWrapper(Minecraft mc)
	{
		this.mc = mc;
		this.provider = new WorldProviderWrapper(mc);
	}
	
	public boolean nonNull()
	{
		return mc.world != null;
	}
}
