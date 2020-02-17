package cookiedragon.luchadora.mixin.mixins.entity

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.client.Render2dEvent
import cookiedragon.luchadora.event.client.UpdateLightmapEvent
import cookiedragon.luchadora.event.entity.EntityTurnEvent
import cookiedragon.luchadora.event.entity.GetEntitiesMouseOverEvent
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.client.renderer.EntityRenderer
import cookiedragon.luchadora.managers.PerspectiveManager
import net.minecraft.entity.Entity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.*
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(EntityRenderer::class)
class MixinEntityRenderer {
	@Inject(method = ["updateCameraAndRender"], at = [At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.AFTER)], require = 1)
	private fun gameOverlayWrapper(partialTicks: Float, nanoTime: Long, ci: CallbackInfo) {
		EventDispatcher.dispatch(Render2dEvent())
	}
	
	@Inject(method = ["updateLightmap"], at = [At(value = "HEAD", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V")], require = 1)
	private fun updateLightmapWrapper(partialTicks: Float, ci: CallbackInfo) {
		EventDispatcher.dispatch(UpdateLightmapEvent())
	}
	
	@Redirect(method = ["orientCamera"], at = At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"), require = 5)
	private fun getRotationYawWrapper(entity: Entity): Float {
		return PerspectiveManager.getYaw()
	}
	
	@Redirect(method = ["orientCamera"], at = At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"), require = 4)
	private fun getRotationPitchWrapper(entity: Entity): Float {
		return PerspectiveManager.getPitch()
	}
	
	@Redirect(method = ["getMouseOver"], at = At(value = "INVOKE", target = "Ljava/util/List;size()I", remap = false), require = 1)
	private fun getNumEntities(list: List<*>): Int {
		with(GetEntitiesMouseOverEvent(list as List<Entity>)) {
			EventDispatcher.dispatch(this)
			return size
		}
	}
	
	@Redirect(method = ["updateCameraAndRender"], at = At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"), require = 1)
	private fun onTurnPlayerWrapper(playerSP: EntityPlayerSP, yaw: Float, pitch: Float) {
		with(EntityTurnEvent.Pre(playerSP, yaw, pitch)) {
			EventDispatcher.dispatch(this)
			if (!isCancelled) {
				entity.turn(yaw, pitch)
				EventDispatcher.dispatch(EntityTurnEvent.Post(entity, yaw, pitch))
			}
		}
	}
}
