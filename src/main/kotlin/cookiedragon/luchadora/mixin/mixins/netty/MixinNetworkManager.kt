package cookiedragon.luchadora.mixin.mixins.netty

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.network.PacketEvent
import cookiedragon.luchadora.event.network.PacketEvent.Receive
import cookiedragon.luchadora.event.network.PacketEvent.Send
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import net.minecraft.network.NetworkManager
import net.minecraft.network.Packet
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(NetworkManager::class)
class MixinNetworkManager {
	@Shadow
	private val channel: Channel? = null
	
	@Inject(method = ["sendPacket(Lnet/minecraft/network/Packet;)V"], at = [At("HEAD")], cancellable = true, require = 1)
	private fun onPacketSendHead(packetIn: Packet<*>, ci: CallbackInfo) {
		with(Send.Pre(packetIn)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
	
	@Inject(method = ["sendPacket(Lnet/minecraft/network/Packet;)V"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun onPacketSendReturn(packetIn: Packet<*>, ci: CallbackInfo) {
		with(Send.Post(packetIn)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
	
	@Inject(method = ["channelRead0"], at = [At("HEAD")], cancellable = true, require = 1)
	private fun onPacketReadHead(context: ChannelHandlerContext, packetIn: Packet<*>, ci: CallbackInfo) {
		with(Receive.Pre(packetIn)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
	
	@Inject(method = ["channelRead0"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun onPacketReadReturn(context: ChannelHandlerContext, packetIn: Packet<*>, ci: CallbackInfo) {
		with(Receive.Post(packetIn)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
}
