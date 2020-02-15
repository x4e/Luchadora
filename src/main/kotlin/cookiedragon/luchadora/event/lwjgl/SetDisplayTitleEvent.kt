package cookiedragon.luchadora.event.lwjgl

import cookiedragon.luchadora.event.CancellableEvent

/**
 * @author cookiedragon234 12/Jan/2020
 */
data class SetDisplayTitleEvent(val oldTitle: String, var newTitle: String): CancellableEvent()
