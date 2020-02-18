package cookiedragon.luchadora.module.dev

import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.ChatUtils
import cookiedragon.valuesystem.Value
import net.minecraft.launchwrapper.Launch
import net.minecraft.launchwrapper.LaunchClassLoader
import net.minecraft.network.play.client.CPacketPlayer

/**
 * @author cookiedragon234 10/Jan/2020
 */
class InvalidTeleportModule : AbstractModule("Invalid Teleport", "Sends invalid numbers to the server causing unpredicted actions", Category.DEV) {
	private val modeVal = Value("Value", ValueMode.NAN)
	
	private enum class ValueMode {
		NAN,
		INFINITY,
		NEG_INFINITY,
		ZERO,
		MIN,
		MAX
	}
	
	override fun onEnabled() {
		LaunchClassLoader::class.java.getDeclaredField("invalidClasses").apply {
			isAccessible = true
			println("CoreMod Load")
			println(get(Launch.classLoader))
		}
		
		setEnabled(false)
		if (mc.isSingleplayer) {
			ChatUtils.printMessage("Cant use in SP")
			return
		}
		
		val pos = when (modeVal.value) {
			ValueMode.NAN 			-> Double.NaN
			ValueMode.INFINITY 		-> Double.POSITIVE_INFINITY
			ValueMode.NEG_INFINITY 	-> Double.NEGATIVE_INFINITY
			ValueMode.ZERO 			-> 0.0
			ValueMode.MIN 			-> Double.MIN_VALUE
			ValueMode.MAX 			-> Double.MAX_VALUE
			else					-> throw IllegalStateException()
		}
		mc.connection!!.sendPacket(CPacketPlayer.Position(pos, pos, pos, mc.player.onGround))
		ChatUtils.printMessage("Sent Position $pos")
	}
}
