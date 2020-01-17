package cookiedragon.luchadora.event.render

import cookiedragon.luchadora.event.api.AbstractEvent
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.util.math.BlockPos

/**
 * @author cookiedragon234 14/Jan/2020
 */
@AbstractEvent.Cancellable
data class RenderBarrierEvent(val worldClient: WorldClient, val x: Int, val y: Int, val z: Int): AbstractEvent()

@AbstractEvent.Cancellable
class RenderBarrierParticleEvent: AbstractEvent()
