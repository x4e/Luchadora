package cookiedragon.luchadora.mixin.mixins.misc

import com.google.common.collect.ImmutableSet
import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.client.AllowedCharactersEvent
import cookiedragon.luchadora.event.client.AllowedCharactersEvent.State.*
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
object MixinChatAllowedCharacters {
	private val serverUnallowedChars: Set<Char> = ImmutableSet.copyOf(arrayOf('/', '\n', '\r', '\t', '\u0000', '\u000C', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'))
	@Inject(method = ["filterAllowedCharacters"], at = [At("HEAD")], cancellable = true)
	private fun isAllowedCharWrapper(input: String, cir: CallbackInfoReturnable<String>) {
		with(AllowedCharactersEvent(DISALLOW)) {
			EventDispatcher.dispatch(this)
			when(state) {
				ALLOW -> {
					cir.returnValue = input
					cir.cancel()
				}
				ALLOW_SERVER -> {
					StringBuilder().also {
						for (c0 in input.toCharArray()) {
							if (!serverUnallowedChars.contains(c0)) {
								it.append(c0)
							}
						}
						cir.returnValue = it.toString()
						cir.cancel()
					}
				}
				else -> {}
			}
		}
	}
}
