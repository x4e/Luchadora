package cookiedragon.luchadora.managers;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.client.KeyPressedEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.ModuleManager;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class BindManager
{
	public static void init()
	{
		EventDispatcher.register(BindManager.class);
		EventDispatcher.subscribe(BindManager.class);
	}
	
	@Subscriber
	private static void onKeyPress(KeyPressedEvent event)
	{
		for (AbstractModule module : ModuleManager.getModules())
		{
			if (module.getKeyBind().getValue() == event.getKey())
			{
				module.toggle();
			}
		}
	}
}
