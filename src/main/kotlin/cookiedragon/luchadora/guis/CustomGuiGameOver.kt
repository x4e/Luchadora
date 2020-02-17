package cookiedragon.luchadora.guis

import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiGameOver
import net.minecraft.util.text.ITextComponent
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.fml.common.ObfuscationReflectionHelper
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import java.io.IOException

/**
 * @author cookiedragon234 17/Feb/2020
 */
class CustomGuiGameOver(old: GuiGameOver) : GuiGameOver(getCauseOfDeath(old)) {
	override fun initGui() {
		super.initGui()
		buttonList.add(GuiButton(3, width / 2 - 100, height / 4 + 120, "[LD] Exit Screen"))
		for (guiButton in buttonList) {
			guiButton.enabled = true
		}
	}
	
	@Throws(IOException::class)
	override fun actionPerformed(button: GuiButton) {
		if (button.id == 3) {
			mc.displayGuiScreen(null)
			mc.player.isDead = false
			mc.player.health = 20f
			onGuiClosed()
			return
		}
		super.actionPerformed(button)
	}
	
	companion object {
		private fun getCauseOfDeath(screen: GuiGameOver): ITextComponent =
				ObfuscationReflectionHelper.getPrivateValue<Any, GuiGameOver>(
						GuiGameOver::class.java, screen, "field_184871_f"
				) as ITextComponent
	}
}
