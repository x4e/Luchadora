package cookiedragon.luchadora.util

import cookiedragon.luchadora.util.Globals.mc
import cookiedragon.luchadora.util.MovementUtils.isMoving
import cookiedragon.luchadora.util.Globals.mc
import cookiedragon.luchadora.util.Globals.mc







/**
 * @author cookiedragon234 14/Jan/2020
 */
object MovementUtils: Globals {
	fun getSpeed(): Float {
		return Math.sqrt(mc.player.motionX * mc.player.motionX + mc.player.motionZ * mc.player.motionZ).toFloat()
	}
	
	fun strafe() {
		strafe(getSpeed())
	}
	
	fun strafe(speed: Float) {
		if (!isMoving())
			return
		
		val yaw = getDirection()
		mc.player.motionX = -Math.sin(yaw) * speed
		mc.player.motionZ = Math.cos(yaw) * speed
	}
	
	fun isMoving(): Boolean {
		return mc.player.movementInput.moveForward != 0f && mc.player.movementInput.moveStrafe != 0f
	}
	
	fun getDirection(): Double {
		var rotationYaw = mc.player.rotationYaw
		
		if (mc.player.moveForward < 0f)
			rotationYaw += 180f
		
		var forward = 1f
		if (mc.player.moveForward < 0f)
			forward = -0.5f
		else if (mc.player.moveForward > 0f)
			forward = 0.5f
		
		if (mc.player.moveStrafing > 0f)
			rotationYaw -= 90f * forward
		
		if (mc.player.moveStrafing < 0f)
			rotationYaw += 90f * forward
		
		return Math.toRadians(rotationYaw.toDouble())
	}
}
