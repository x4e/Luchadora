package cookiedragon.luchadora.mixin.mixins.world

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.render.RenderBarrierEvent
import cookiedragon.luchadora.event.render.RenderBarrierParticleEvent
import net.minecraft.client.multiplayer.WorldClient
import net.minecraft.util.EnumParticleTypes
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(WorldClient::class)
class MixinWorldClient {
	@Inject(method = ["doVoidFogParticles"], at = [At("HEAD")], cancellable = true, require = 1)
	private fun doFogWrapper(posX: Int, posY: Int, posZ: Int, ci: CallbackInfo) {
		with(RenderBarrierEvent((this as WorldClient), posX, posY, posZ)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) {
				ci.cancel()
			}
		}
	}
	
	@Redirect(method = ["showBarrierParticles"], at = At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;spawnParticle(Lnet/minecraft/util/EnumParticleTypes;DDDDDD[I)V"), require = 1)
	private fun spawnBarrierParticleWrapper(worldClient: WorldClient, particleType: EnumParticleTypes, xCoord: Double, yCoord: Double, zCoord: Double, xSpeed: Double, ySpeed: Double, zSpeed: Double, vararg parameters: Int) {
		with(RenderBarrierParticleEvent()) {
			EventDispatcher.dispatch(this)
			if (!isCancelled) {
				worldClient.spawnParticle(particleType, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, *parameters)
			}
		}
	}
}
