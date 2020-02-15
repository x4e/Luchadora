package cookiedragon.luchadora.event.client;

import cookiedragon.luchadora.util.Key;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class KeyPressedEvent
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
