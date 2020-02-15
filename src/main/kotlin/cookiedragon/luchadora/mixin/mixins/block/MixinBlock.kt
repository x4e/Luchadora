package cookiedragon.luchadora.mixin.mixins.block

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.block.GetBlockRenderLayerEvent
import net.minecraft.block.Block
import net.minecraft.util.BlockRenderLayer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(Block::class)
class MixinBlock {
	@Inject(method = ["getRenderLayer"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun getRenderLayerWrapper(cir: CallbackInfoReturnable<BlockRenderLayer>) {
		this as Block
		with(GetBlockRenderLayerEvent(this, cir.returnValue)) {
			EventDispatcher.dispatch(this)
			cir.returnValue = renderLayer
		}
	}
}
