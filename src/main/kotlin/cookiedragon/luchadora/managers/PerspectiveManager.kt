package cookiedragon.luchadora.managers

import cookiedragon.luchadora.util.Globals.mc
import cookiedragon.luchadora.util.Initialisable
import org.apache.commons.lang3.mutable.MutableFloat
import java.util.function.Consumer

/**
 * @author cookiedragon234 17/Feb/2020
 */
object PerspectiveManager: Initialisable() {
	val yawModifiers: MutableSet<(MutableFloat) -> Unit>
	val pitchModifiers: MutableSet<(MutableFloat) -> Unit>
	
	init {
		yawModifiers = hashSetOf()
		pitchModifiers = hashSetOf()
	}
	
	fun getYaw(): Float {
		val yaw = MutableFloat(mc.player.rotationYaw)
		for (modifier in yawModifiers) {
			modifier(yaw)
		}
		return yaw.toFloat()
	}
	
	fun getPitch(): Float {
		val pitch = MutableFloat(mc.player.rotationPitch)
		for (modifier in pitchModifiers) {
			modifier(pitch)
		}
		return pitch.toFloat()
	}
	
	fun registerYawModifier(modifier: (MutableFloat) -> Unit) {
		yawModifiers.add(modifier)
	}
	
	fun registerPitchModifier(modifier: (MutableFloat) -> Unit) {
		pitchModifiers.add(modifier)
	}
}
