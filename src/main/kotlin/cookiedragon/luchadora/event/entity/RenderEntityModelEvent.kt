package cookiedragon.luchadora.event.entity

import cookiedragon.luchadora.event.CancellableEvent
import net.minecraft.client.model.ModelBase
import net.minecraft.entity.Entity

/**
 * @author cookiedragon234 13/Jan/2020
 */
object RenderEntityModelEvent {
	data class Pre(
			val modelBase: ModelBase,
			val entity: Entity,
			val limbSwing: Float,
			val limbSwingAmount: Float,
			val age: Float,
			val headYaw: Float,
			val headPitch: Float,
			val scale: Float
	): CancellableEvent()
	data class Post(
			val modelBase: ModelBase,
			val entity: Entity,
			val limbSwing: Float,
			val limbSwingAmount: Float,
			val age: Float,
			val headYaw: Float,
			val headPitch: Float,
			val scale: Float
	): CancellableEvent()
}
