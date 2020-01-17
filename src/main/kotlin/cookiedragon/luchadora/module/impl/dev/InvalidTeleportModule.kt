package cookiedragon.luchadora.module.impl.dev

import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.ChatUtils
import cookiedragon.luchadora.value.values.EnumValue
import net.minecraft.network.play.client.CPacketPlayer

/**
 * @author cookiedragon234 10/Jan/2020
 */
class InvalidTeleportModule : AbstractModule("Invalid Teleport", "Sends invalid numbers to the server causing unpredicted actions", Category.DEV) {
	private val modeVal = EnumValue("Value", ValueMode.NAN)
	
	private enum class ValueMode {
		NAN,
		INFINITY,
		NEG_INFINITY,
		ZERO,
		MIN,
		MAX
	}
	
	override fun onEnabled() {
		isEnabled = false
		if (mc.isSingleplayer) {
			ChatUtils.sendMessage("Cant use in SP")
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
		ChatUtils.sendMessage("Sent Position $pos")
	}
}
