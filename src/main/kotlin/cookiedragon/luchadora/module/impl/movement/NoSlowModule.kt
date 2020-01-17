package cookiedragon.luchadora.module.impl.movement

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.entity.SlowEntityUsingEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category

/**
 * @author cookiedragon234 10/Jan/2020
 */
class NoSlowModule : AbstractModule("No Slow", "Dont be slowed down by various actions", Category.MOVEMENT) {
	@Subscriber
	private fun onSlowEntity(event: SlowEntityUsingEvent) {
		event.isCancelled = true
	}
}
