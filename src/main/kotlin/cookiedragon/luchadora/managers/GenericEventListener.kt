package cookiedragon.luchadora.managers

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.Luchadora
import cookiedragon.luchadora.event.lwjgl.SetDisplayTitleEvent
import cookiedragon.luchadora.util.Initialisable

/**
 * @author cookiedragon234 12/Jan/2020
 */
object GenericEventListener: Initialisable() {
	init {
		EventDispatcher.register(this)
		EventDispatcher.subscribe(this)
	}
	
	@Subscriber
	private fun onSetDisplayTitle(event: SetDisplayTitleEvent) {
		event.newTitle = Luchadora.brand
	}
}
