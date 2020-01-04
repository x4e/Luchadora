package cookiedragon.luchadora.module.impl.movement;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.entity.UpdateWalkingPlayerEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.value.values.NumberValue;

/**
 * @author cookiedragon234 01/Jan/2020
 */
@AbstractModule.Declaration(name = "Elytra Fly", description = "Fly without using rockets", category = Category.MOVEMENT)
public class ElytraFlyModule extends AbstractModule
{
	private final NumberValue speed = new NumberValue("Speed", 1f, 0, 20);
	private final NumberValue fall = new NumberValue("Fall Speed", 0.61f, 0, 1);
	
	@Subscriber
	private void onWalkingPlayerUpdate(UpdateWalkingPlayerEvent.Pre event)
	{
		if (mc.player.elytraEquipped() && mc.player.isElytraFlying())
		{
			mc.player.setVelocity(0,0,0);
			mc.player.setJumpMovementFactor(speed.getValue().floatValue());
			
			if (mc.player.getTicksExisted() % 4 == 0)
			{
				mc.player.setMotionY(-fall.getValue().doubleValue());
			}
		}
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
