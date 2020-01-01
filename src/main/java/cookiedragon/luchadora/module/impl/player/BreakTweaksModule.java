package cookiedragon.luchadora.module.impl.player;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.entity.ResetBlockDamageEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.value.Value;
import cookiedragon.luchadora.value.values.BooleanValue;

/**
 * @author cookiedragon234 31/Dec/2019
 */
@AbstractModule.Deceleration(name = "Break Tweaks", description = "Tweak block breaking behaviour", category = Category.PLAYER)
public class BreakTweaksModule extends AbstractModule
{
	private Value<Boolean> stickyBreak = new BooleanValue("Sticky Break", true);
	
	@Subscriber
	private void onResetBlockDamage(ResetBlockDamageEvent event)
	{
		event.setCancelled(stickyBreak.getValue());
	}
	
	@Override
	protected void onEnabled()
	{ }
	
	@Override
	protected void onDisabled()
	{ }
}
