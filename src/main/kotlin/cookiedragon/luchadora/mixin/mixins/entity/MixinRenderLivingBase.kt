package cookiedragon.luchadora.mixin.mixins.entity

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.entity.RenderEntityEvent
import cookiedragon.luchadora.event.entity.RenderEntityModelEvent
import net.minecraft.client.model.ModelBase
import net.minecraft.client.renderer.entity.RenderLivingBase
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.Redirect
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(RenderLivingBase::class)
abstract class MixinRenderLivingBase {
	@Redirect(method = ["renderModel"], at = At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
	private fun renderModelWrapper(modelBase: ModelBase, entityIn: Entity, limbSwing: Float, limbSwingAmount: Float, ageInTicks: Float, netHeadYaw: Float, headPitch: Float, scale: Float) {
		with(RenderEntityModelEvent.Pre(modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)) {
			EventDispatcher.dispatch(this)
			if (isCancelled) return
			modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)
		}
		with(RenderEntityModelEvent.Post(modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale)) {
			EventDispatcher.dispatch(this)
		}
	}
	
	@Inject(method = ["doRender"], at = [At("HEAD")], cancellable = true, require = 1)
	private fun doRenderHead(entity: EntityLivingBase, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float, ci: CallbackInfo) {
		EventDispatcher.dispatch(RenderEntityEvent.Pre(entity, x, y, z, entityYaw, partialTicks))
	}
	
	@Inject(method = ["doRender"], at = [At("RETURN")], require = 1)
	private fun doRenderReturn(entity: EntityLivingBase, x: Double, y: Double, z: Double, entityYaw: Float, partialTicks: Float, ci: CallbackInfo) {
		EventDispatcher.dispatch(RenderEntityEvent.Post(entity, x, y, z, entityYaw, partialTicks))
	}
}
