package cookiedragon.luchadora.managers

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.client.Render3dEvent
import cookiedragon.luchadora.guis.CustomGuiGameOver
import cookiedragon.luchadora.util.Initialisable
import net.minecraft.client.gui.GuiGameOver
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.client.event.GuiOpenEvent
import net.minecraftforge.client.event.RenderWorldLastEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

/**
 * @author cookiedragon234 17/Feb/2020
 */
object ForgeEventListener: Initialisable() {
	init {
		EventDispatcher.register(this)
		EventDispatcher.subscribe(this)
		MinecraftForge.EVENT_BUS.register(this)
	}
	
	var lastChat: String? = null
	
	@SubscribeEvent
	fun onEvent(event: Any) {
		println("Forge event $event")
		
		EventDispatcher.dispatch(event)
	}
	
	@Subscriber
	fun onGuiOpen(event: GuiOpenEvent) {
		if (event.gui is GuiGameOver) {
			event.gui = CustomGuiGameOver(event.gui as GuiGameOver)
		}
	}
	
	@Subscriber
	fun onRenderLast(event: RenderWorldLastEvent) {
		EventDispatcher.dispatch(Render3dEvent())
	}
	
	@Subscriber
	fun onChat(event: ClientChatReceivedEvent) {
		lastChat = event.message.unformattedText
	}
}
