package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.CancellableEvent;

/**
 * @author cookiedragon234 08/Dec/2019
 */
public class SendMessageEvent extends CancellableEvent
{
	private final String message;
	
	public SendMessageEvent(String message)
	{
		this.message = message;
	}
	
	public String getMessage()
	{
		return this.message;
	}
	
	public static class Pre extends SendMessageEvent
	{
		public Pre(String message)
		{
			super(message);
		}
	}
	
	public static class Post extends SendMessageEvent
	{
		public Post(String message)
		{
			super(message);
		}
	}
}
