package cookiedragon.luchadora.event.entity;

import cookiedragon.luchadora.event.api.AbstractEvent;
import net.minecraft.entity.Entity;

import java.util.List;

/**
 * @author cookiedragon234 02/Jan/2020
 */
public class GetEntitiesMouseOverEvent extends AbstractEvent
{
	public int size;
	public final List<Entity> entities;
	
	public GetEntitiesMouseOverEvent(List<Entity> entities)
	{
		this.entities = entities;
		this.size = entities.size();
	}
}
