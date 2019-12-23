package cookiedragon.luchadora.managers;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class ForgeEventListener
{
	public static void init()
	{
		MinecraftForge.EVENT_BUS.register(ForgeEventListener.class);
	}
	
	public static String lastChat = null;
	
	public static void onChat(ClientChatReceivedEvent event)
	{
		lastChat = event.getMessage().getUnformattedText();
	}
}
