package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.CancellableEvent;
import net.minecraft.client.entity.EntityPlayerSP;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class SlowEntityUsingEvent extends CancellableEvent
{
	public final EntityPlayerSP entity;
	
	public SlowEntityUsingEvent(EntityPlayerSP entity)
	{
		this.entity = entity;
	}
}
