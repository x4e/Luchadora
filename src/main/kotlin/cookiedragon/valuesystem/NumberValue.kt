package cookiedragon.valuesystem

/**
 * @author cookiedragon234 16/Feb/2020
 */
class NumberValue<T: Number>(
		name: String,
		defaultVal: T,
		val min: T,
		val max: T,
		val slowUpdate: Boolean
): Value<T>(name, defaultVal) {
	constructor(
		name: String,
		defaultVal: T,
		min: T,
		max: T
	): this(name, defaultVal, min, max, false)
}
