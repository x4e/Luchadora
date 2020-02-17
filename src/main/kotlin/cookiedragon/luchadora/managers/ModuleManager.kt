package cookiedragon.luchadora.managers

import com.google.common.reflect.ClassPath
import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.event.luchadora.ModuleInitialisationEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.CategoryElement
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.GuiModule
import cookiedragon.luchadora.util.Initialisable
import cookiedragon.valuesystem.Value
import net.minecraft.launchwrapper.Launch
import java.lang.reflect.Modifier

/**
 * @author cookiedragon234 17/Feb/2020
 */
@Suppress("UnstableApiUsage")
object ModuleManager: Initialisable(), Iterable<AbstractModule> {
	private val modules: MutableMap<AbstractModule, List<Value<*>>>
	
	init {
		modules = hashMapOf()
		
		EventDispatcher.dispatch(ModuleInitialisationEvent.Pre())
		
		for (classInfo in ClassPath.from(Launch.classLoader).allClasses) {
			if (classInfo.name.startsWith("cookiedragon.luchadora.module")) {
				try {
					val aClass = classInfo.load()
					if (!Modifier.isAbstract(aClass.modifiers) && AbstractModule::class.java.isAssignableFrom(aClass)) {
						for (constructor in aClass.constructors) {
							if (constructor.parameterCount == 0) {
								val instance = constructor.newInstance() as AbstractModule
								modules[instance] = instance.values
								break
							}
						}
					}
				} catch (t: Throwable) {
					t.printStackTrace()
				}
			}
		}
		
		with(ModuleManager.getModule(GuiModule::class.java)) {
			for (category in Category::class.java.enumConstants) {
				CategoryElement(category, this).also {
					modules[it] = it.values
				}
			}
		}
		
		println("Dispatching post init")
		EventDispatcher.dispatch(ModuleInitialisationEvent.Post())
		
		println(modules)
	}
	
	@JvmStatic
	fun <T : AbstractModule> getModule(moduleClazz: Class<T>): T {
		for (module in modules.keys) {
			if (moduleClazz.isAssignableFrom(module.javaClass)) return moduleClazz.cast(module)
		}
		throw RuntimeException("Module $moduleClazz was not initialised!")
	}
	
	@JvmStatic
	fun findValueForModule(module: AbstractModule, valueName: String): Value<*> {
		for (value in modules[module]!!) {
			if (value.name.equals(valueName, ignoreCase = true)) return value
		}
		throw RuntimeException("Couldn't find value '" + valueName + "' for module '" + module.name + "'")
	}
	
	@JvmStatic
	fun getModules(): Set<AbstractModule> {
		return modules.keys
	}
	
	@JvmStatic
	fun getAll(): Map<AbstractModule, List<Value<*>>> {
		return modules
	}
	
	@JvmStatic
	fun getValuesForModule(module: AbstractModule): List<Value<*>>? {
		return modules[module]
	}
	
	override fun iterator(): Iterator<AbstractModule> = modules.keys.iterator()
}
