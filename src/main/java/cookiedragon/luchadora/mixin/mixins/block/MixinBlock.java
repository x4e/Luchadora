package cookiedragon.luchadora.mixin.mixins.block;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.block.GetBlockRenderLayerEvent;
import net.minecraft.block.Block;
import net.minecraft.util.BlockRenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author cookiedragon234 14/Jan/2020
 */
@Mixin(Block.class)
public class MixinBlock
{
	@Inject(method = "getRenderLayer", at = @At("RETURN"), cancellable = true, require = 1)
	private void getRenderLayerWrapper(CallbackInfoReturnable<BlockRenderLayer> cir)
	{
		GetBlockRenderLayerEvent event = new GetBlockRenderLayerEvent((Block)(Object)this, cir.getReturnValue());
		EventDispatcher.dispatch(event);
		cir.setReturnValue(event.getRenderLayer());
	}
}
