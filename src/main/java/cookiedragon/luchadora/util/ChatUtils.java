package cookiedragon.luchadora.util;

import net.minecraft.util.text.TextComponentString;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class ChatUtils implements Globals
{
	public static void sendMessage(String message)
	{
		mc.player.sendMessage(new TextComponentString(message));
	}
}
