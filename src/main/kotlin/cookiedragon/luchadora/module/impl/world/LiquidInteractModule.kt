package cookiedragon.luchadora.module.impl.world

import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.block.CanLiquidCollideEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category

/**
 * @author cookiedragon234 13/Jan/2020
 */
class LiquidInteractModule: AbstractModule("Liquid Interact", "Interact with liquids as if they were solid", Category.WORLD) {
	@Subscriber
	private fun onLiquidInteract(event: CanLiquidCollideEvent) {
		event.returnVal = true
	}
}
