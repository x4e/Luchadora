package cookiedragon.luchadora.command

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.CommandNode
import com.mojang.brigadier.tree.RootCommandNode
import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.util.Globals
import net.minecraft.client.Minecraft

/**
 * @author cookiedragon234 18/Feb/2020
 */
abstract class AbstractCommand {
	protected val mc: Minecraft = Globals.mc
	
	init {
		EventDispatcher.register(this)
		EventDispatcher.subscribe(this)
	}
	
	abstract fun <T> register(dispatcher: CommandDispatcher<T>)
	
	fun <T> registerNodes(dispatcher: CommandDispatcher<T>, vararg nodes: CommandNode<T>) {
		for (node in nodes) {
			dispatcher.root.addChild(node)
		}
	}
}

class RootCommandProvider<T>: RootCommandNode<T>() {

}
