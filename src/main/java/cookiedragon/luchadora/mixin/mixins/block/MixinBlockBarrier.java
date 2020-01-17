package cookiedragon.luchadora.mixin.mixins.block;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.block.GetBlockRenderTypeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author cookiedragon234 14/Jan/2020
 */
@Mixin(BlockBarrier.class)
public class MixinBlockBarrier
{
	@Inject(method = "getRenderType", at = @At("RETURN"), cancellable = true, require = 1)
	private void getRenderTypeWrapper(IBlockState state, CallbackInfoReturnable<EnumBlockRenderType> cir)
	{
		GetBlockRenderTypeEvent event = new GetBlockRenderTypeEvent((Block)(Object)this, state, cir.getReturnValue());
		EventDispatcher.dispatch(event);
		cir.setReturnValue(event.getRenderType());
	}
}
