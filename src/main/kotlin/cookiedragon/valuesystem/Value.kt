package cookiedragon.valuesystem

import cookiedragon.luchadora.util.ISerializable
import java.util.function.BiConsumer

/**
 * @author cookiedragon234 15/Feb/2020
 */
open class Value<T>(val name: String, defaultVal: T) {
	var description: String? = null
	var value = defaultVal
		private set
	/**
	 * called when the value of this value is set using the setValue function
	 */
	protected var callback: ((T, T) -> Unit)? = null
	
	fun addCallback(callback: ((T, T) -> Unit)?): Value<T> =
		this.apply {
			this.callback = callback
		}
	
	fun setValue(value: T, bypassCallback: Boolean = false): T {
		val oldVal = this.value
		this.value = value
		if (!bypassCallback) callback?.invoke(oldVal, this.value)
		return value
	}
	
	fun castAndSetValue(value: Any): T {
		return this.setValue(value as T)
	}
	
	override fun toString() = "$name ${javaClass.name} $value"
}
