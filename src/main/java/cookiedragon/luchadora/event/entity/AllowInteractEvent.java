package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.api.AbstractEvent;

/**
 * @author cookiedragon234 31/Dec/2019
 */
public class AllowInteractEvent extends AbstractEvent
{
	public boolean isUsingItem;
	
	public AllowInteractEvent(boolean isUsingItem)
	{
		this.isUsingItem = isUsingItem;
	}
}
