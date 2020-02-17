package cookiedragon.luchadora.event.entity

import cookiedragon.luchadora.event.CancellableEvent

/**
 * @author cookiedragon234 17/Feb/2020
 */
open class SendMessageEvent(open val message: String) : CancellableEvent() {
	data class Pre(override val message: String) : SendMessageEvent(message)
	data class Post(override val message: String) : SendMessageEvent(message)
}
