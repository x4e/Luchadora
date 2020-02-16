package cookiedragon.luchadora.module.impl.player

import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.entity.EntityReachEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.valuesystem.NumberValue

/**
 * @author cookiedragon234 11/Jan/2020
 */
class ReachModule : AbstractModule("Reach", "Reach further", Category.PLAYER) {
	private val reachDistance = NumberValue("Distance", 3.5f, 0, 6)
	
	@Subscriber
	private fun onReach(event: EntityReachEvent) {
		event.reachDistance = reachDistance.value.toFloat()
	}
}
