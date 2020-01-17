package cookiedragon.luchadora.event.entity

import cookiedragon.luchadora.event.api.AbstractEvent
import cookiedragon.luchadora.mixin.mixins.entity.MixinRenderLivingBase
import net.minecraft.client.model.ModelBase
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase

/**
 * @author cookiedragon234 13/Jan/2020
 */
object RenderEntityModelEvent {
	@AbstractEvent.Cancellable
	data class Pre(
			val modelBase: ModelBase,
			val entity: Entity,
			val limbSwing: Float,
			val limbSwingAmount: Float,
			val age: Float,
			val headYaw: Float,
			val headPitch: Float,
			val scale: Float
	): AbstractEvent()
	data class Post(
			val modelBase: ModelBase,
			val entity: Entity,
			val limbSwing: Float,
			val limbSwingAmount: Float,
			val age: Float,
			val headYaw: Float,
			val headPitch: Float,
			val scale: Float
	): AbstractEvent()
}
