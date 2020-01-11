package cookiedragon.luchadora.module.impl.player

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.entity.EntityTurnEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.value.values.EnumValue
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.util.EnumFacing

/**
 * @author cookiedragon234 11/Jan/2020
 */
@AbstractModule.Declaration(name = "Rotation Lock", description = "Lock your yaw", category = Category.PLAYER)
class RotationLockModule: AbstractModule() {
	private val typeSetting = EnumValue("Mode", RotationLockType.DONT_SNAP)
			.addCallback { oldVal, newVal -> recalculate(newVal)
			}
	
	private enum class RotationLockType {
		DONT_SNAP,
		NORTH,
		EAST,
		SOUTH,
		WEST
	}
	
	private var yaw: Float? = null
	
	override fun onEnabled() {
		recalculate(typeSetting.value)
	}
	
	private fun recalculate(value: Enum<*>) {
		if (!this.isEnabled)
			return
		
		yaw = when (value) {
			RotationLockType.DONT_SNAP -> return
			RotationLockType.NORTH 		-> EnumFacing.NORTH.horizontalAngle
			RotationLockType.EAST 		-> EnumFacing.EAST.horizontalAngle
			RotationLockType.SOUTH 		-> EnumFacing.SOUTH.horizontalAngle
			RotationLockType.WEST 		-> EnumFacing.WEST.horizontalAngle
			else 						-> return
		}
		mc.player.setRotation(yaw!!, mc.player.rotationPitch)
		if (mc.player.ridingEntity != null)
			mc.player.ridingEntity.rotationYaw = yaw!!
	}
	
	override fun onDisabled() {
		yaw = null
	}
	
	@Subscriber
	private fun onTurnEntity(event: EntityTurnEvent.Pre) {
		if (event.entity is EntityPlayerSP) {
			event.yaw = 0f
		}
	}
}
