package cookiedragon.luchadora.mixin.mixins.lwjgl

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.lwjgl.SetDisplayTitleEvent
import org.lwjgl.opengl.Display
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(Display::class)
class MixinDisplay {
	@Shadow(remap = false)
	private val title: String? = null
	
	@Inject(method = ["setTitle"], at = [At("HEAD")], cancellable = true, remap = false, require = 1)
	private fun onSetTitle(newTitle: String, ci: CallbackInfo) {
		with(SetDisplayTitleEvent(title ?: "", newTitle)) {
			EventDispatcher.dispatch(this)
			if (newTitle != newTitle) {
				ci.cancel()
				Display.setTitle(newTitle)
			}
		}
	}
}
