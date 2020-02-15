package cookiedragon.luchadora.mixin.mixins.entity

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.entity.EntityReachEvent
import cookiedragon.luchadora.event.entity.ResetBlockDamageEvent
import net.minecraft.client.multiplayer.PlayerControllerMP
import net.minecraft.util.math.BlockPos
import net.minecraft.world.border.WorldBorder
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(PlayerControllerMP::class)
class MixinPlayerControllerMP {
	@Inject(method = ["resetBlockRemoving"], at = [At("HEAD")], cancellable = true, require = 1)
	private fun resetBlockWrapper(ci: CallbackInfo) {
		with(ResetBlockDamageEvent()) {
			EventDispatcher.dispatch(this)
			if (isCancelled) ci.cancel()
		}
	}
	
	@Inject(method = ["getBlockReachDistance"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun getReachDistanceWrapper(cir: CallbackInfoReturnable<Float>) {
		this as PlayerControllerMP
		with(EntityReachEvent(this, cir.returnValue)) {
			EventDispatcher.dispatch(this)
			cir.returnValue = reachDistance
		}
	}
	
	// Experimental: Bypass world border protection? Seems to have some of an effect
	@Redirect(method = ["processRightClickBlock"], at = At(value = "INVOKE", target = "Lnet/minecraft/world/border/WorldBorder;contains(Lnet/minecraft/util/math/BlockPos;)Z"), require = 1)
	private fun isInWorldBorder(worldBorder: WorldBorder, pos: BlockPos): Boolean {
		return true
	}
	
	@Redirect(method = ["clickBlock"], at = At(value = "INVOKE", target = "Lnet/minecraft/world/border/WorldBorder;contains(Lnet/minecraft/util/math/BlockPos;)Z"), require = 1)
	private fun isInWorldBorderClick(worldBorder: WorldBorder, pos: BlockPos): Boolean {
		return true
	}
}
