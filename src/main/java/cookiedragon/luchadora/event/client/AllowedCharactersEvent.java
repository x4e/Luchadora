package cookiedragon.luchadora.event.client;

/**
 * @author cookiedragon234 24/Dec/2019
 */
public class AllowedCharactersEvent
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
