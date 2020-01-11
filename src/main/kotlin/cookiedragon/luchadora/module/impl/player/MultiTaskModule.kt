package cookiedragon.luchadora.module.impl.player

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.entity.AllowInteractEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category

/**
 * @author cookiedragon234 11/Jan/2020
 */
@AbstractModule.Declaration(name = "MultiTask", description = "Peform multiple actions at once", category = Category.PLAYER)
class MultiTaskModule : AbstractModule() {
	@Subscriber
	private fun allowInteract(event: AllowInteractEvent) {
		event.isUsingItem = false
	}
}

