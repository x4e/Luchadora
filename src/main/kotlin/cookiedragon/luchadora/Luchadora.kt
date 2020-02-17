package cookiedragon.luchadora

import cookiedragon.luchadora.integrations.discord.DiscordIntegration
import cookiedragon.luchadora.managers.*
import cookiedragon.luchadora.module.impl.ui.HudManager
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.awt.Frame
import javax.swing.JOptionPane

/**
 * @author cookiedragon234 17/Feb/2020
 */
@Mod(modid = Luchadora.MOD_ID, name = Luchadora.MOD_NAME, version = Luchadora.VERSION)
class Luchadora {
	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
	}
	
	@Mod.EventHandler
	fun init(event: FMLInitializationEvent) {
		HudManager.init()
		for (toInit in arrayOf(
				BindManager,
				ModuleManager,
				GenericEventListener,
				ForgeEventListener,
				PerspectiveManager,
				DiscordIntegration
		)) { toInit.init() }
	}
	
	@Mod.EventHandler
	fun postInit(event: FMLPostInitializationEvent) {
	}
	
	companion object {
		const val MOD_ID = "luchadora"
		const val MOD_NAME = "Luchadora"
		const val VERSION = "0.1"
		@Mod.Instance(MOD_ID)
		var INSTANCE: Luchadora? = null
		
		@JvmStatic
		val brand: String
			get() = String.format("%s %s", MOD_NAME, VERSION)
	}
}
