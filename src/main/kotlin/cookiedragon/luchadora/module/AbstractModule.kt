package cookiedragon.luchadora.module

import cookiedragon.eventsystem.EventDispatcher
import cookiedragon.luchadora.util.Globals
import cookiedragon.luchadora.util.Initialisable
import cookiedragon.luchadora.util.Key
import cookiedragon.valuesystem.KeyValue
import cookiedragon.valuesystem.Value
import net.minecraft.client.Minecraft

/**
 * @author cookiedragon234 17/Feb/2020
 */
abstract class AbstractModule(
		val name: String,
		val description: String,
		val category: Category
) : Initialisable() {
	@JvmField
	val enabled = Value("Enabled", false)
	val visible = Value("Visible", true)
	val keyBind: Value<Key> = KeyValue("Keybind", Key.KEY_NONE)
	
	val mc: Minecraft = Globals.mc
	
	init {
		EventDispatcher.register(this)
		enabled.description = description
		enabled.addCallback { oldValue: Boolean?, newValue: Boolean ->
			if (newValue) {
				EventDispatcher.subscribe(this)
				onEnabled()
			} else {
				EventDispatcher.unsubscribe(this)
				onDisabled()
			}
		}
		
		var annotation = this.javaClass.getAnnotation(Declaration::class.java)
		if (annotation != null) {
			enabled.setValue(annotation.defaultOn, true)
			visible.setValue(annotation.defaultVisible && category.visible, true)
			keyBind.setValue(annotation.defaultBind, true)
		}
	}
	
	protected open fun onEnabled(){}
	protected open fun onDisabled(){}
	
	
	
	fun setEnabled(enabled: Boolean) {
		this.enabled.setValue(enabled)
	}
	val isEnabled: Boolean
		get() = enabled.value
	
	fun toggle(): Boolean {
		return enabled.setValue(!enabled.value)
	}
	
	val values: List<Value<*>> by lazy {
		val values = arrayListOf<Value<*>>()
		val clazzes = arrayListOf<Class<*>>()
		
		var clazz: Class<*> = javaClass
		do {
			clazzes.add(clazz)
		} while (clazz.superclass.also { clazz = it } != null && AbstractModule::class.java.isAssignableFrom(clazz))
		
		for (i in clazzes.indices.reversed()) {
			clazz = clazzes[i]
			for (declaredField in clazz.declaredFields) {
				with (declaredField) {
					if (Value::class.java.isAssignableFrom(type)) {
						isAccessible = true
						values.add(this[this@AbstractModule] as Value<*>)
					}
				}
			}
		}
		values
	}
	
	override fun toString() = this.name
}


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class Declaration(val defaultOn: Boolean = false, val defaultVisible: Boolean = false, val defaultBind: Key = Key.KEY_NONE)
