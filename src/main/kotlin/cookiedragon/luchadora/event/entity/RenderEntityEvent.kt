package cookiedragon.luchadora.event.entity

import net.minecraft.entity.EntityLivingBase

/**
 * @author cookiedragon234 13/Jan/2020
 */
object RenderEntityEvent {
	data class Pre(
		val entity: EntityLivingBase,
		val x: Double,
		val y: Double,
		val z: Double,
		val yaw: Float,
		val partialTicks: Float
	)
	data class Post(
			val entity: EntityLivingBase,
			val x: Double,
			val y: Double,
			val z: Double,
			val yaw: Float,
			val partialTicks: Float
	)
}
