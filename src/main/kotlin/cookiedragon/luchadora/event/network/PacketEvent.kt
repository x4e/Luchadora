package cookiedragon.luchadora.event.network

import cookiedragon.luchadora.event.CancellableEvent
import net.minecraft.network.Packet

/**
 * @author cookiedragon234 17/Feb/2020
 */
open class PacketEvent private constructor(open val packet: Packet<*>) : CancellableEvent() {
	open class Send private constructor(packet: Packet<*>) : PacketEvent(packet) {
		data class Pre(override val packet: Packet<*>) : Send(packet)
		data class Post(override val packet: Packet<*>) : Send(packet)
	}
	
	open class Receive private constructor(packet: Packet<*>) : PacketEvent(packet) {
		data class Pre(override val packet: Packet<*>) : Receive(packet)
		data class Post(override val packet: Packet<*>) : Receive(packet)
	}
}
