package cookiedragon.luchadora.mixin.mixins.entity;

import cookiedragon.luchadora.event.entity.SendMessageEvent;
import cookiedragon.luchadora.event.api.EventDispatcher;

import cookiedragon.luchadora.event.entity.SlowEntityUsingEvent;
import cookiedragon.luchadora.event.entity.UpdateWalkingPlayerEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 08/Dec/2019
 */
@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP
{
	@Inject(method = "sendChatMessage", at = @At(value = "HEAD"), cancellable = true, require = 1)
	private void sendMessagePreWrapper(String message, CallbackInfo ci)
	{
		SendMessageEvent.Pre event  = new SendMessageEvent.Pre(message);
		EventDispatcher.dispatch(event);
		
		if (event.isCancelled())
		{
			ci.cancel();
		}
	}
	
	@Inject(method = "sendChatMessage", at = @At(value = "RETURN"), require = 1)
	private void sendMessagePostWrapper(String message, CallbackInfo ci)
	{
		SendMessageEvent.Post event  = new SendMessageEvent.Post(message);
		EventDispatcher.dispatch(event);
	}
	
	@Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z", ordinal = 0), require = 1)
	private boolean shouldSlowDownWrapper(EntityPlayerSP playerSP)
	{
		if (playerSP.isHandActive())
		{
			SlowEntityUsingEvent event = new SlowEntityUsingEvent(playerSP);
			EventDispatcher.dispatch(event);
			return !event.isCancelled();
		}
		return false;
	}
	
	@Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true, require = 1)
	private void onWalkingUpdatePre(CallbackInfo ci)
	{
		UpdateWalkingPlayerEvent.Pre event = new UpdateWalkingPlayerEvent.Pre();
		EventDispatcher.dispatch(event);
		if (event.isCancelled())
			ci.cancel();
	}
	
	@Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"), require = 1)
	private void onWalkingUpdatePost(CallbackInfo ci)
	{
		UpdateWalkingPlayerEvent.Post event = new UpdateWalkingPlayerEvent.Post();
		EventDispatcher.dispatch(event);
	}
}
