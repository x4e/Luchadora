package cookiedragon.luchadora.module.impl.dev

import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.ChatUtils
import cookiedragon.luchadora.value.values.EnumValue
import net.minecraft.network.play.client.CPacketPlayer
import java.text.MessageFormat

/**
 * @author cookiedragon234 10/Jan/2020
 */
@AbstractModule.Declaration(name = "Invalid Teleport", description = "Attempts to teleport to invalid positions, causing unpredicted server actions", category = Category.DEV)
class InvalidTeleportModule : AbstractModule() {
	
	private val modeVal = EnumValue("Mode", Mode.NAN)
	
	private enum class Mode {
		NAN,
		INFINITY,
		NEG_INFINITY,
		ZERO,
		MIN,
		MAX
	}
	
	override fun onEnabled() {
		if (mc.isSinglePlayer) {
			ChatUtils.sendMessage("Cant use in SP")
			isEnabled = false
			return
		}
		
		var pos: Double? = null
		when {
			modeVal.value === Mode.NAN 			-> pos = java.lang.Double.NaN
			modeVal.value === Mode.INFINITY 	-> pos = java.lang.Double.POSITIVE_INFINITY
			modeVal.value === Mode.NEG_INFINITY -> pos = java.lang.Double.NEGATIVE_INFINITY
			modeVal.value === Mode.ZERO 		-> pos = 0.0
			modeVal.value === Mode.MIN 			-> pos = java.lang.Double.MIN_VALUE
			modeVal.value === Mode.MAX 			-> pos = java.lang.Double.MAX_VALUE
		}
		mc.connection.sendPacket(CPacketPlayer.Position(pos!!, pos, pos, mc.player.onGround()))
		ChatUtils.sendMessage("Sent Position $pos")
		isEnabled = false
	}
}
