package cookiedragon.luchadora.managers

import com.google.common.reflect.ClassPath
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.ParseResults
import com.mojang.brigadier.exceptions.CommandSyntaxException
import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.command.AbstractCommand
import cookiedragon.luchadora.command.RootCommandProvider
import cookiedragon.luchadora.event.entity.SendMessageEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.util.ChatUtils
import cookiedragon.luchadora.util.Initialisable
import net.minecraft.launchwrapper.Launch
import java.lang.Exception
import java.lang.reflect.Modifier

/**
 * @author cookiedragon234 18/Feb/2020
 */
object CommandManager: Initialisable() {
	var prefix = "."
	var dispatcher = CommandDispatcher<Any?>(RootCommandProvider())
	var commands = arrayListOf<AbstractCommand>()
	
	init {
		EventDispatcher.register(this)
		EventDispatcher.subscribe(this)
		
		for (classInfo in ClassPath.from(Launch.classLoader).allClasses) {
			if (classInfo.name.startsWith("cookiedragon.luchadora.command")) {
				try {
					val aClass = classInfo.load()
					if (!Modifier.isAbstract(aClass.modifiers) && AbstractCommand::class.java.isAssignableFrom(aClass)) {
						for (constructor in aClass.constructors) {
							if (constructor.parameterCount == 0) {
								val instance = constructor.newInstance() as AbstractCommand
								commands.add(instance)
								break
							}
						}
					}
				} catch (t: Throwable) {
					t.printStackTrace()
				}
			}
		}
		
		for (command in commands) {
			command.register(dispatcher)
			println("Registered $command")
		}
	}
	
	@Subscriber
	private fun onMessage(event: SendMessageEvent.Pre) {
		val msg = event.message.trim()
		println("On Message $msg")
		if (msg.startsWith(prefix)) {
			event.isCancelled = true
			try {
				execute(msg.substring(prefix.length))
			} catch (e: CommandSyntaxException) {
				ChatUtils.printMessage(e.message.toString())
				e.printStackTrace()
			} catch (e: Exception) {
				ChatUtils.printMessage("Error while executing: ${e.message}")
				e.printStackTrace()
			}
		}
	}
	
	@Throws(CommandSyntaxException::class)
	fun execute(command: String) = dispatcher.execute(parse(command))
	
	fun parse(command: String): ParseResults<Any?> = dispatcher.parse(command, null)
}
