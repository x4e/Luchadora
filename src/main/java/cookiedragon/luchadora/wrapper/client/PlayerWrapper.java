package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class PlayerWrapper
{
	private final Minecraft mc;
	
	public PlayerWrapper(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public void sendMessage(ITextComponent component)
	{
		mc.player.sendMessage(component);
	}
	
	public boolean onGround()
	{
		return mc.player.onGround;
	}
}
