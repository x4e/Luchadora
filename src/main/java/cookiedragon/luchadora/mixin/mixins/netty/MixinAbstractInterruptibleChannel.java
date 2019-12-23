package cookiedragon.luchadora.mixin.mixins.netty;

import io.netty.channel.socket.nio.NioSocketChannel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.channels.spi.AbstractInterruptibleChannel;

/**
 * @author cookiedragon234 22/Dec/2019
 */
@Mixin(AbstractInterruptibleChannel.class)
public class MixinAbstractInterruptibleChannel
{
	@Inject(method = "close", at = @At("HEAD"))
	private void onCloseWrapper(CallbackInfo ci)
	{
		new RuntimeException("Interruptible Channel Closed").printStackTrace();
	}
}
