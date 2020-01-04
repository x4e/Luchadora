package cookiedragon.luchadora.event.network;

import cookiedragon.luchadora.event.api.AbstractEvent;
import net.minecraft.network.Packet;

/**
 * @author cookiedragon234 02/Jan/2020
 */
@AbstractEvent.Cancellable
public class PacketEvent extends AbstractEvent
{
	public final Packet<?> packet;
	
	private PacketEvent(Packet<?> packet)
	{
		this.packet = packet;
	}
	
	public static class Send extends PacketEvent
	{
		private Send(Packet<?> packet)
		{
			super(packet);
		}
		
		public static class Pre extends Send
		{
			public Pre(Packet<?> packet)
			{
				super(packet);
			}
		}
		
		public static class Post extends Send
		{
			public Post(Packet<?> packet)
			{
				super(packet);
			}
		}
	}
	
	public static class Receive extends PacketEvent
	{
		private Receive(Packet<?> packet)
		{
			super(packet);
		}
		
		public static class Pre extends Receive
		{
			public Pre(Packet<?> packet)
			{
				super(packet);
			}
		}
		
		public static class Post extends Receive
		{
			public Post(Packet<?> packet)
			{
				super(packet);
			}
		}
	}
}
