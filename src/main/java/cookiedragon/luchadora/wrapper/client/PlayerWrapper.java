package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.text.ITextComponent;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class PlayerWrapper
{
	private EntityPlayerSP player;
	
	public PlayerWrapper(EntityPlayerSP player)
	{
		this.player = player;
	}
	
	public void sendMessage(ITextComponent component)
	{
		this.player.sendMessage(component);
	}
}
