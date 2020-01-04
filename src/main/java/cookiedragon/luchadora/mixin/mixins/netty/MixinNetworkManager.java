package cookiedragon.luchadora.mixin.mixins.netty;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.network.PacketEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 22/Dec/2019
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager
{
	@Shadow
	private Channel channel;
	
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true, require = 1)
	private void onPacketSendHead(Packet<?> packetIn, CallbackInfo ci)
	{
		PacketEvent.Send.Pre event = new PacketEvent.Send.Pre(packetIn);
		EventDispatcher.dispatch(event);
		if (event.isCancelled())
			ci.cancel();
	}
	
	@Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("RETURN"), cancellable = true, require = 1)
	private void onPacketSendReturn(Packet<?> packetIn, CallbackInfo ci)
	{
		PacketEvent.Send.Post event = new PacketEvent.Send.Post(packetIn);
		EventDispatcher.dispatch(event);
		if (event.isCancelled())
			ci.cancel();
	}
	
	@Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true, require = 1)
	private void onPacketReadHead(final ChannelHandlerContext context, final Packet<?> packetIn, final CallbackInfo ci)
	{
		PacketEvent.Receive.Pre event = new PacketEvent.Receive.Pre(packetIn);
		EventDispatcher.dispatch(event);
		if (event.isCancelled())
			ci.cancel();
	}
	
	@Inject(method = "channelRead0", at = @At("RETURN"), cancellable = true, require = 1)
	private void onPacketReadReturn(final ChannelHandlerContext context, final Packet<?> packetIn, final CallbackInfo ci)
	{
		PacketEvent.Receive.Post event = new PacketEvent.Receive.Post(packetIn);
		EventDispatcher.dispatch(event);
		if (event.isCancelled())
			ci.cancel();
	}
}
