package cookiedragon.luchadora.mixin.mixins.world;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.render.RenderBarrierEvent;
import cookiedragon.luchadora.event.render.RenderBarrierParticleEvent;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * @author cookiedragon234 14/Jan/2020
 */
@Mixin(WorldClient.class)
public class MixinWorldClient
{
	@Inject(method = "doVoidFogParticles", at = @At("HEAD"), cancellable = true, require = 1)
	private void doFogWrapper(int posX, int posY, int posZ, CallbackInfo ci)
	{
		RenderBarrierEvent event = new RenderBarrierEvent((WorldClient)(Object)this, posX, posY, posZ);
		EventDispatcher.dispatch(event);
		if (event.isCancelled())
		{
			ci.cancel();
		}
	}
	
	@Redirect(
		method = "showBarrierParticles",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"),
		require = 1
	)
	private void spawnBarrierParticleWrapper(WorldClient worldClient, EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
	{
		RenderBarrierParticleEvent event = new RenderBarrierParticleEvent();
		EventDispatcher.dispatch(event);
		if (!event.isCancelled())
		{
			worldClient.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
		}
	}
}
