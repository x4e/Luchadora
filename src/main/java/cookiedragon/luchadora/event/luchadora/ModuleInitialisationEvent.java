package cookiedragon.luchadora.event.luchadora;

import cookiedragon.luchadora.event.api.AbstractEvent;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class ModuleInitialisationEvent extends AbstractEvent
{
	public static class Pre extends ModuleInitialisationEvent
	{}
	
	public static class Post extends ModuleInitialisationEvent
	{}
}
