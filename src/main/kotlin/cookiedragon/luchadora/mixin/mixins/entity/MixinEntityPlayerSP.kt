package cookiedragon.luchadora.mixin.mixins.entity

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.entity.SendMessageEvent
import cookiedragon.luchadora.event.entity.SlowEntityUsingEvent
import cookiedragon.luchadora.event.entity.UpdateWalkingPlayerEvent
import net.minecraft.client.entity.EntityPlayerSP
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(EntityPlayerSP::class)
class MixinEntityPlayerSP {
	@Inject(method = ["sendChatMessage"], at = [At(value = "HEAD")], cancellable = true, require = 1)
	private fun sendMessagePreWrapper(message: String, ci: CallbackInfo) {
		with(SendMessageEvent.Pre(message)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
	
	@Inject(method = ["sendChatMessage"], at = [At(value = "RETURN")], require = 1)
	private fun sendMessagePostWrapper(message: String, ci: CallbackInfo) {
		EventDispatcher.dispatch(SendMessageEvent.Post(message))
	}
	
	@Redirect(method = ["onLivingUpdate"], at = At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z", ordinal = 0), require = 1)
	private fun shouldSlowDownWrapper(playerSP: EntityPlayerSP): Boolean {
		if (playerSP.isHandActive) {
			with(SlowEntityUsingEvent(playerSP)) {
				EventDispatcher.dispatch(this)
				return !isCancelled
			}
		}
		return false
	}
	
	@Inject(method = ["onUpdateWalkingPlayer"], at = [At("HEAD")], cancellable = true, require = 1)
	private fun onWalkingUpdatePre(ci: CallbackInfo) {
		with(UpdateWalkingPlayerEvent.Pre()) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
	
	@Inject(method = ["onUpdateWalkingPlayer"], at = [At("RETURN")], require = 1)
	private fun onWalkingUpdatePost(ci: CallbackInfo) {
		EventDispatcher.dispatch(UpdateWalkingPlayerEvent.Post())
	}
}
