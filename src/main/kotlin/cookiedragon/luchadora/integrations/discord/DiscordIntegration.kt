package cookiedragon.luchadora.integrations.discord

import com.jagrosh.discordipc.IPCClient
import com.jagrosh.discordipc.IPCListener
import com.jagrosh.discordipc.entities.RichPresence
import cookiedragon.luchadora.managers.ForgeEventListener.lastChat
import cookiedragon.luchadora.util.Globals
import cookiedragon.luchadora.util.Globals.mc
import cookiedragon.luchadora.util.Initialisable
import org.json.JSONObject

/**
 * @author cookiedragon234 17/Feb/2020
 */

class DiscordIntegration : Initialisable() {
	private val ipcClient: IPCClient = IPCClient(658340490655039537L)
	
	init {
		ipcClient.setListener(object : IPCListener {
			override fun onReady(client: IPCClient) {
				println("Connected to discord " + client.discordBuild)
				DiscordPresenceThread.start(client)
				println("Started Discord RPC")
			}
			
			override fun onClose(client: IPCClient, json: JSONObject) {
				println("Discord connection closed")
			}
			
			override fun onDisconnect(client: IPCClient, t: Throwable) {
				println("Discord connection disconnected")
			}
		})
		try {
			ipcClient.connect()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}


class DiscordPresenceThread(private val client: IPCClient) : Runnable, Globals {
	private var richPresence: RichPresence? = null
	override fun run() {
		while (true) {
			try {
				richPresence = RichPresence.Builder()
						.setState(getState())
						.setDetails(getDetails())
						.setLargeImage("luchadora", "Luchadora")
						.build()
			} catch(e: Exception) {
				e.printStackTrace()
			} finally {
				if (richPresence != null) client.sendRichPresence(richPresence)
			}
			try { Thread.sleep(3500) } catch (ignored: Exception) {}
		}
	}
	
	private val discord = "discord/fUkXhEu"
	fun getState(): String {
		val ip = mc.currentServerData?.serverIP ?: ""
		return when {
			mc.world == null -> discord
			mc.isSingleplayer -> "SinglePlayer"
			ip.contains("2b2t") -> {
				if (lastChat?.startsWith("Position in queue: ") == true)
					"$ip ${lastChat?.substring("Position in queue: ".length)} in q"
				else ip
			}
			else -> ip
		}
	}
	
	fun getDetails(): String {
		return when {
			mc.isSingleplayer -> "SinglePlayer"
			mc.world == null -> "Main Menu"
			else -> "Multiplayer"
		}
	}
	
	companion object {
		fun start(client: IPCClient) {
			Thread(DiscordPresenceThread(client)).start()
		}
	}
	
}

