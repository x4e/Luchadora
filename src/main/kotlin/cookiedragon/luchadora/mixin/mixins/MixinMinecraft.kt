package cookiedragon.luchadora.mixin.mixins

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.client.KeyPressedEvent
import cookiedragon.luchadora.event.entity.AllowInteractEvent
import cookiedragon.luchadora.util.Key
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.multiplayer.PlayerControllerMP
import net.minecraft.client.settings.GameSettings
import org.lwjgl.input.Keyboard
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(Minecraft::class)
abstract class MixinMinecraft {
	@Shadow
	var gameSettings: GameSettings? = null
	
	@Shadow
	protected abstract fun clickMouse()
	
	@Shadow
	protected abstract fun middleClickMouse()
	
	@Shadow
	protected abstract fun rightClickMouse()
	
	@Shadow
	var player: EntityPlayerSP? = null
	@Shadow
	var playerController: PlayerControllerMP? = null
	
	@Inject(method = ["runTickKeyboard"], at = [At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V")])
	private fun onKeyPressWrapper(ci: CallbackInfo) {
		if (Keyboard.getEventKeyState()) {
			EventDispatcher.dispatch(KeyPressedEvent(Key.fromCode(Keyboard.getEventKey())))
		}
	}
	
	@Redirect(method = ["sendClickBlockToController"], at = At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
	private fun isHandActiveWrapper(playerSP: EntityPlayerSP): Boolean {
		with(AllowInteractEvent(playerSP.isHandActive)) {
			EventDispatcher.dispatch(this)
			return isUsingItem
		}
	}
	
	@Redirect(method = ["rightClickMouse"], at = At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0), require = 1)
	private fun isHittingBlockWrapper(playerControllerMP: PlayerControllerMP): Boolean {
		with(AllowInteractEvent(playerControllerMP.isHittingBlock)) {
			EventDispatcher.dispatch(this)
			return isUsingItem
		}
	}
}
