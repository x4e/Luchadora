package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.api.AbstractEvent;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * @author cookiedragon234 01/Jan/2020
 */
@AbstractEvent.Cancellable
public class SlowEntityUsingEvent extends AbstractEvent
{
	public final EntityPlayerSP entity;
	
	public SlowEntityUsingEvent(EntityPlayerSP entity)
	{
		this.entity = entity;
	}
}
