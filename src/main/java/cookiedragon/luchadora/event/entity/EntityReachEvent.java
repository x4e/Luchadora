package cookiedragon.luchadora.event.entity;

import net.minecraft.client.multiplayer.PlayerControllerMP;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class EntityReachEvent
{
	public final PlayerControllerMP playerController;
	public float reachDistance;
	
	public EntityReachEvent(PlayerControllerMP playerController, float reachDistance)
	{
		this.playerController = playerController;
		this.reachDistance = reachDistance;
	}
}
