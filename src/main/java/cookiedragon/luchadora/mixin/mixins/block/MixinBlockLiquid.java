package cookiedragon.luchadora.mixin.mixins.block;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.block.CanLiquidCollideEvent;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author cookiedragon234 13/Jan/2020
 */
@Mixin(BlockLiquid.class)
public class MixinBlockLiquid
{
	@Inject(method = "canCollideCheck", at = @At("RETURN"), cancellable = true, require = 1)
	private void canCollideWrapper(IBlockState state, boolean hitIfLiquid, CallbackInfoReturnable<Boolean> cir)
	{
		CanLiquidCollideEvent event = new CanLiquidCollideEvent((BlockLiquid)(Object)this, state, hitIfLiquid, cir.getReturnValue());
		EventDispatcher.dispatch(event);
		cir.setReturnValue(event.getReturnVal());
	}
}
