package cookiedragon.luchadora.module.impl.player

import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.entity.GetEntitiesMouseOverEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category

/**
 * @author cookiedragon234 12/Jan/2020
 */
class NoEntityTraceModule : AbstractModule("No Entity Trace", "You can hit blocks through entities", Category.PLAYER) {
	@Subscriber
	private fun onEntityTrace(event: GetEntitiesMouseOverEvent) {
		event.size = 0
	}
}
