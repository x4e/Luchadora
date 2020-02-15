package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.CancellableEvent;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class UpdateWalkingPlayerEvent extends CancellableEvent
{
	public static class Pre extends UpdateWalkingPlayerEvent{}
	
	public static class Post extends UpdateWalkingPlayerEvent{}
}
