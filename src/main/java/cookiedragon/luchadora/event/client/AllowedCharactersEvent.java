package cookiedragon.luchadora.event.client;

import cookiedragon.luchadora.event.api.AbstractEvent;

/**
 * @author cookiedragon234 24/Dec/2019
 */
public class AllowedCharactersEvent extends AbstractEvent
{
	public enum State
	{
		ALLOW(),
		ALLOW_SERVER(),
		DISALLOW()
	}
	
	public State state;
	
	public AllowedCharactersEvent(State state)
	{
		this.state = state;
	}
}
