package cookiedragon.luchadora.integration.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.RichPresence;
import cookiedragon.luchadora.managers.ForgeEventListener;
import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.wrapper.client.ServerDataWrapper;
import net.minecraft.client.multiplayer.ServerData;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class DiscordPrecenceThread implements Runnable, Globals
{
	public static void start(IPCClient client)
	{
		new Thread(new DiscordPrecenceThread(client)).start();
	}
	
	private static final String DISCORD = "discord/fUkXhEu";
	private final IPCClient client;
	
	public DiscordPrecenceThread(IPCClient client)
	{
		this.client = client;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			RichPresence richPresence = new RichPresence.Builder()
				.setState(DISCORD)
				.setDetails("Loading...")
				.setLargeImage("luchadora", "Luchadora")
				.build();
			try
			{
				richPresence = new RichPresence.Builder()
					.setState(getState())
					.setDetails(getDetails())
					.setLargeImage("luchadora", "Luchadora")
					.build();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				client.sendRichPresence(richPresence);
			}
			
			try
			{
				Thread.sleep(3500);
			}
			catch (Exception ignored) {}
		}
	}
	
	public static String getState()
	{
		ServerData currentServerData = mc.getCurrentServerData();
		if (currentServerData == null || !currentServerData.serverIP.isEmpty())
		{
			return DISCORD;
		}
		
		String state = currentServerData.serverIP;
		
		if (state.contains("2b2t.org"))
		{
			String lastChat = ForgeEventListener.lastChat;
			
			if (lastChat != null && lastChat.startsWith("Position in queue: "))
			{
				state += " " + lastChat.substring("Position in queue: ".length()) + " in q";
			}
		}
		else if (currentServerData.populationInfo != null)
		{
			state += " " + currentServerData.populationInfo;
		}
		
		return state;
	}
	
	public static String getDetails()
	{
		if (mc.isSingleplayer())
		{
			return "SinglePlayer";
		}
		
		ServerData currentServerData = mc.getCurrentServerData();
		if (currentServerData == null || !currentServerData.serverIP.isEmpty())
		{
			return "Main Menu";
		}
		
		return "Multiplayer";
	}
}
