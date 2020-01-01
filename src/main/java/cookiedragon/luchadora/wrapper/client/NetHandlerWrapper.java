package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

/**
 * @author cookiedragon234 31/Dec/2019
 */
public class NetHandlerWrapper
{
	private final Minecraft mc;
	
	public NetHandlerWrapper(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public void sendPacket(Packet<?> packet)
	{
		mc.getConnection().sendPacket(packet);
	}
}
