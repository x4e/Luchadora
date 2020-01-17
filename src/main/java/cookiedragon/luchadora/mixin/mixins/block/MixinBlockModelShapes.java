package cookiedragon.luchadora.mixin.mixins.block;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.block.GetBlockModelEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.IBakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author cookiedragon234 14/Jan/2020
 */
@Mixin(BlockModelShapes.class)
public class MixinBlockModelShapes
{
	@Inject(method = "getModelForState", at = @At("RETURN"), cancellable = true, require = 1)
	private void getModelWrapper(IBlockState state, CallbackInfoReturnable<IBakedModel> cir)
	{
		GetBlockModelEvent event = new GetBlockModelEvent(state, cir.getReturnValue());
		EventDispatcher.dispatch(event);
		cir.setReturnValue(event.getModel());
	}
}
