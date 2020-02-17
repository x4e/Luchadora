package cookiedragon.luchadora.event.entity

import cookiedragon.luchadora.event.CancellableEvent
import net.minecraft.client.entity.EntityPlayerSP

/**
 * @author cookiedragon234 17/Feb/2020
 */
data class SlowEntityUsingEvent(val entity: EntityPlayerSP) : CancellableEvent()
