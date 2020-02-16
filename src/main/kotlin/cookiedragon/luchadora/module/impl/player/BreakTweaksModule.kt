package cookiedragon.luchadora.module.impl.player

import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.entity.ResetBlockDamageEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.valuesystem.Value

/**
 * @author cookiedragon234 11/Jan/2020
 */
class BreakTweaksModule : AbstractModule("Break Tweaks", "Tweak block breaking behaviour", Category.PLAYER) {
	private val stickyBreak = Value("Sticky Break", true)
	
	@Subscriber
	private fun onResetBlockDamage(event: ResetBlockDamageEvent) {
		event.isCancelled = stickyBreak.value
	}
}

