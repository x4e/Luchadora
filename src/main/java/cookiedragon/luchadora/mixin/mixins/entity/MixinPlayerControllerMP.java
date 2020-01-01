package cookiedragon.luchadora.mixin.mixins.entity;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.entity.ResetBlockDamageEvent;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.network.NetworkManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 22/Dec/2019
 */
@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP
{
	@Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
	private void resetBlockWrapper(CallbackInfo ci)
	{
		ResetBlockDamageEvent event = new ResetBlockDamageEvent();
		EventDispatcher.dispatch(event);
		if(!event.isCancelled())
		{
			ci.cancel();
		}
	}
}
