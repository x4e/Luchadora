package cookiedragon.luchadora.event.client;

import cookiedragon.luchadora.event.api.AbstractEvent;
import cookiedragon.luchadora.util.Key;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class KeyPressedEvent extends AbstractEvent
{
	private final Key key;
	
	public KeyPressedEvent(Key key)
	{
		this.key = key;
	}
	
	public Key getKey()
	{
		return key;
	}
}
