package cookiedragon.luchadora.event.entity

import cookiedragon.luchadora.event.CancellableEvent

/**
 * @author cookiedragon234 17/Feb/2020
 */
open class UpdateWalkingPlayerEvent : CancellableEvent() {
	class Pre : UpdateWalkingPlayerEvent()
	class Post : UpdateWalkingPlayerEvent()
}
