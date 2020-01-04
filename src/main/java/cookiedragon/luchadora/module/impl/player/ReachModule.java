package cookiedragon.luchadora.module.impl.player;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.entity.EntityReachEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.value.values.NumberValue;

/**
 * @author cookiedragon234 01/Jan/2020
 */
@AbstractModule.Declaration(name = "Reach", description = "Reach further", category = Category.PLAYER)
public class ReachModule extends AbstractModule
{
	private final NumberValue reachDistance = new NumberValue("Distance", 3.5f, 0, 6);
	
	@Subscriber
	private void onReach(EntityReachEvent event)
	{
		event.reachDistance = reachDistance.getValue().floatValue();
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
