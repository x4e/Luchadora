package cookiedragon.luchadora.event.entity;

import net.minecraft.entity.Entity;

import java.util.List;

/**
 * @author cookiedragon234 02/Jan/2020
 */
public class GetEntitiesMouseOverEvent
{
	public int size;
	public final List<Entity> entities;
	
	public GetEntitiesMouseOverEvent(List<Entity> entities)
	{
		this.entities = entities;
		this.size = entities.size();
	}
}
