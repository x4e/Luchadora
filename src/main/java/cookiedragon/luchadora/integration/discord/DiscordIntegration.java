package cookiedragon.luchadora.integration.discord;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.Packet;
import com.jagrosh.discordipc.entities.User;
import cookiedragon.luchadora.integration.IIntegration;
import org.json.JSONObject;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class DiscordIntegration implements IIntegration
{
	IPCClient ipcClient;
	
	@Override
	public void start()
	{
		ipcClient = new IPCClient(658340490655039537L);
		ipcClient.setListener(new IPCListener()
		{
			@Override
			public void onReady(IPCClient client)
			{
				System.out.println("Connected to discord " + client.getDiscordBuild());
				
				DiscordPrecenceThread.start(client);
				System.out.println("Started Discord RPC");
			}
			
			@Override
			public void onActivityJoin(IPCClient client, String secret)
			{
				System.out.println("Requesting to join activity " + secret);
			}
			
			@Override
			public void onClose(IPCClient client, JSONObject json)
			{
				System.out.println("Discord connection closed");
			}
			
			@Override
			public void onDisconnect(IPCClient client, Throwable t)
			{
				System.out.println("Discord connection disconnected");
			}
		});
		try
		{
			ipcClient.connect();
		}
		catch (Exception e)
		{
			new RuntimeException("Failure Starting RP", e).printStackTrace();
		}
	}
}
