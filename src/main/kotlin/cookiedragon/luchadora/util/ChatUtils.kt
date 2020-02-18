package cookiedragon.luchadora.util

import cookiedragon.luchadora.util.Globals.mc
import net.minecraft.util.text.TextComponentString

/**
 * @author cookiedragon234 17/Feb/2020
 */
object ChatUtils {
	fun printMessage(message: String) {
		mc.player.sendMessage(TextComponentString(message))
	}
}
