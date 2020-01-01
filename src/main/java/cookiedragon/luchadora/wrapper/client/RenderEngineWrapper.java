package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author cookiedragon234 26/Dec/2019
 */
public class RenderEngineWrapper
{
	private final Minecraft mc;
	
	public RenderEngineWrapper(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public void bindTexture(ResourceLocation resource)
	{
		mc.getTextureManager().bindTexture(resource);
	}
}
