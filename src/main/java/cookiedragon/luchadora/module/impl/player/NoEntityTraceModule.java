package cookiedragon.luchadora.module.impl.player;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.entity.GetEntitiesMouseOverEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;

/**
 * @author cookiedragon234 02/Jan/2020
 */
@AbstractModule.Declaration(name = "No Entity Trace", description = "You can hit blocks through entities", category = Category.PLAYER)
public class NoEntityTraceModule extends AbstractModule
{
	@Subscriber
	private void onEntityTrace(GetEntitiesMouseOverEvent event)
	{
		event.size = 0;
	}
}
