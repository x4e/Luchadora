package cookiedragon.luchadora.mixin.mixins.block

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.block.GetBlockModelEvent
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.BlockModelShapes
import net.minecraft.client.renderer.block.model.IBakedModel
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(BlockModelShapes::class)
class MixinBlockModelShapes {
	@Inject(method = ["getModelForState"], at = [At("RETURN")], cancellable = true, require = 1)
	private fun getModelWrapper(state: IBlockState, cir: CallbackInfoReturnable<IBakedModel>) {
		with(GetBlockModelEvent(state, cir.returnValue)) {
			EventDispatcher.dispatch(this)
			cir.returnValue = model
		}
	}
}
