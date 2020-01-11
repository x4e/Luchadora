package cookiedragon.luchadora.module.impl.player

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.entity.ResetBlockDamageEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.value.values.BooleanValue

/**
 * @author cookiedragon234 11/Jan/2020
 */
@AbstractModule.Declaration(name = "Break Tweaks", description = "Tweak block breaking behaviour", category = Category.PLAYER)
class BreakTweaksModule : AbstractModule() {
	private val stickyBreak = BooleanValue("Sticky Break", true)
	
	@Subscriber
	private fun onResetBlockDamage(event: ResetBlockDamageEvent) {
		event.isCancelled = stickyBreak.value
	}
}

