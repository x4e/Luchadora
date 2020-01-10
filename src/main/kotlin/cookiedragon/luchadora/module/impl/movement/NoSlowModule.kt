package cookiedragon.luchadora.module.impl.movement

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.entity.SlowEntityUsingEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category

/**
 * @author cookiedragon234 10/Jan/2020
 */
@AbstractModule.Declaration(name = "No Slow", description = "Dont be slowed down by various actions", category = Category.MOVEMENT)
class NoSlowModule : AbstractModule() {
	@Subscriber
	private fun onSlowEntity(event: SlowEntityUsingEvent) {
		event.isCancelled = true
	}
}
