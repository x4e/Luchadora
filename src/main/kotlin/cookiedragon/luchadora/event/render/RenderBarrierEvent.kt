package cookiedragon.luchadora.event.render

import cookiedragon.luchadora.event.CancellableEvent
import net.minecraft.client.multiplayer.WorldClient

/**
 * @author cookiedragon234 14/Jan/2020
 */
data class RenderBarrierEvent(val worldClient: WorldClient, val x: Int, val y: Int, val z: Int): CancellableEvent()

class RenderBarrierParticleEvent: CancellableEvent()
