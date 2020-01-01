package cookiedragon.luchadora.module.impl.player;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.entity.AllowInteractEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;

/**
 * @author cookiedragon234 31/Dec/2019
 */
@AbstractModule.Deceleration(name = "MultiTask", description = "Peform multiple actions at once", category = Category.PLAYER)
public class MultiTaskModule extends AbstractModule
{
	@Subscriber
	private void allowInteract(AllowInteractEvent event)
	{
		event.isUsingItem = false;
	}
	
	@Override
	protected void onEnabled()
	{
	
	}
	
	@Override
	protected void onDisabled()
	{
	
	}
}
