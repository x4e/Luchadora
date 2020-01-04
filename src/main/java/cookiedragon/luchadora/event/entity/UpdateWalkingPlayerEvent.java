package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.api.AbstractEvent;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class UpdateWalkingPlayerEvent extends AbstractEvent
{
	@Cancellable
	public static class Pre extends UpdateWalkingPlayerEvent{}
	
	public static class Post extends UpdateWalkingPlayerEvent{}
}
