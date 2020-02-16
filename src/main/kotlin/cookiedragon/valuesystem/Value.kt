package cookiedragon.valuesystem

import cookiedragon.luchadora.util.ISerializable
import java.util.function.BiConsumer

/**
 * @author cookiedragon234 15/Feb/2020
 */
abstract class Value<T>(val name: String, defaultVal: T) {
	var description: String? = null
	var value = defaultVal
		private set
	protected var callback // called when the value of this value is set using the setValue function
		: BiConsumer<T, T>? = null
	
	/**
	 * @param callback old value, new value
	 */
	fun addCallback(callback: BiConsumer<T, T>?): Value<T> {
		this.callback = callback
		return this
	}
	
	@JvmOverloads
	fun setValue(value: T, bypassCallback: Boolean = false) {
		val oldVal = this.value
		this.value = value
		if (!bypassCallback && callback != null) callback!!.accept(oldVal, this.value)
	}
	
	override fun toString() = "$name ${javaClass.name} $value"
}
