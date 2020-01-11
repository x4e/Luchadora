package cookiedragon.luchadora.event.entity

import cookiedragon.luchadora.event.api.AbstractEvent
import net.minecraft.entity.Entity

/**
 * @author cookiedragon234 11/Jan/2020
 */
object EntityTurnEvent {
	@AbstractEvent.Cancellable
	data class Pre(val entity: Entity, var yaw: Float, var pitch: Float): AbstractEvent()
	
	data class Post(val entity: Entity, val yaw: Float, val pitch: Float): AbstractEvent()
}
