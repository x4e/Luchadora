package cookiedragon.luchadora.event.lwjgl

import cookiedragon.luchadora.event.api.AbstractEvent

/**
 * @author cookiedragon234 12/Jan/2020
 */
@AbstractEvent.Cancellable
data class SetDisplayTitleEvent(val oldTitle: String, var newTitle: String): AbstractEvent()
