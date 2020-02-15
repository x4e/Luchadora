package cookiedragon.luchadora.mixin.mixins.block

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.block.GetBlockRenderTypeEvent
import net.minecraft.block.Block
import net.minecraft.block.BlockBarrier
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumBlockRenderType
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(BlockBarrier::class)
class MixinBlockBarrier {
	@Inject(method = ["getRenderType"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun getRenderTypeWrapper(state: IBlockState, cir: CallbackInfoReturnable<EnumBlockRenderType>) {
		this as Block
		with(GetBlockRenderTypeEvent(this, state, cir.returnValue)) {
			EventDispatcher.dispatch(this)
			cir.returnValue = renderType
		}
	}
}
