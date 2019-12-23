package cookiedragon.luchadora.mixin.mixins.entity;

import cookiedragon.luchadora.event.entity.SendMessageEvent;
import cookiedragon.luchadora.event.api.EventDispatcher;

import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 08/Dec/2019
 */
@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP
{
	@Inject(method = "sendChatMessage", at = @At(value = "HEAD"), cancellable = true)
	private void sendMessagePreWrapper(String message, CallbackInfo ci)
	{
		SendMessageEvent.Pre event  = new SendMessageEvent.Pre(message);
		EventDispatcher.dispatch(event);
		
		if (event.isCancelled())
		{
			ci.cancel();
		}
	}
	
	@Inject(method = "sendChatMessage", at = @At(value = "RETURN"))
	private void sendMessagePostWrapper(String message, CallbackInfo ci)
	{
		SendMessageEvent.Post event  = new SendMessageEvent.Post(message);
		EventDispatcher.dispatch(event);
	}
}
