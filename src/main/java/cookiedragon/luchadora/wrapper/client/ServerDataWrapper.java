package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.multiplayer.ServerData;

import java.util.regex.Pattern;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class ServerDataWrapper
{
	private final ServerData serverData;
	
	public ServerDataWrapper(ServerData serverData)
	{
		this.serverData = serverData;
	}
	
	public String getIp()
	{
		return serverData.serverIP;
	}
	
	public String getPopulationInfo()
	{
		return serverData.populationInfo;
	}
	
	public int[] getFormattedPopulationInfo()
	{
		int[] out = new int[]{0,0};
		String pop = getPopulationInfo();
		
		if (pop == null || pop.isEmpty())
			return null;
		
		String[] split = pop.split(Pattern.quote("/"));
		if (split.length >= 2)
		{
			out[0] = Integer.parseInt(split[0]);
			out[1] = Integer.parseInt(split[1]);
		}
		
		return out;
	}
}
