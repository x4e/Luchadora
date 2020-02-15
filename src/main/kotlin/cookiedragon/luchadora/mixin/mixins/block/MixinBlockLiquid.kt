package cookiedragon.luchadora.mixin.mixins.block

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.block.CanLiquidCollideEvent
import net.minecraft.block.BlockLiquid
import net.minecraft.block.state.IBlockState
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(BlockLiquid::class)
class MixinBlockLiquid {
	@Inject(method = ["canCollideCheck"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun canCollideWrapper(state: IBlockState, hitIfLiquid: Boolean, cir: CallbackInfoReturnable<Boolean>) {
		this as BlockLiquid
		with(CanLiquidCollideEvent(this, state, hitIfLiquid, cir.returnValue)) {
			EventDispatcher.dispatch(this)
			cir.returnValue = returnVal
		}
	}
}
