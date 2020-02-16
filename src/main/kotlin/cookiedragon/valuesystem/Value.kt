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
	protected var callback // called when the value of this value is set using the setValue function
		: ((T, T) -> Unit)? = null
	
	/**
	 * @param callback old value, new value
	 */
	fun addCallback(callback: ((T, T) -> Unit)?): Value<T> {
		this.callback = callback
		return this
	}
	
	@JvmOverloads
	fun setValue(value: T, bypassCallback: Boolean = false) {
		val oldVal = this.value
		this.value = value
		if (!bypassCallback && callback != null) callback!!(oldVal, this.value)
	}
	
	fun castAndSetValue(value: Any) {
		this.setValue(value as T)
	}
	
	override fun toString() = "$name ${javaClass.name} $value"
}
