package cookiedragon.luchadora.event.entity;

/**
 * @author cookiedragon234 31/Dec/2019
 */
public class AllowInteractEvent
{
	public boolean isUsingItem;
	
	public AllowInteractEvent(boolean isUsingItem)
	{
		this.isUsingItem = isUsingItem;
	}
}
