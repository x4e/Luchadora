package cookiedragon.luchadora.mixin.mixins.misc

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.client.AllowedCharactersEvent
import net.minecraft.util.ChatAllowedCharacters
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(ChatAllowedCharacters::class)
class MixinChatAllowedCharacters {
	companion object {
		@JvmStatic
		@Inject(method = ["filterAllowedCharacters"], at = [At("HEAD")], cancellable = true, require = 1)
		private fun isAllowedCharWrapper(input: String, cir: CallbackInfoReturnable<String>) {
			with(AllowedCharactersEvent()) {
				EventDispatcher.dispatch(this)
				if (forceAllow) {
					cir.returnValue = input
					cir.cancel()
				}
			}
		}
	}
}
