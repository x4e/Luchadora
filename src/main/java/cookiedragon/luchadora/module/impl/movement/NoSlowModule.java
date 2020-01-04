package cookiedragon.luchadora.module.impl.movement;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.entity.SlowEntityUsingEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;

/**
 * @author cookiedragon234 01/Jan/2020
 */
@AbstractModule.Declaration(name = "No Slow", description = "Dont be slowed down by various actions", category = Category.MOVEMENT)
public class NoSlowModule extends AbstractModule
{
	@Subscriber
	private void onSlowEntity(SlowEntityUsingEvent event)
	{
		event.setCancelled(true);
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
