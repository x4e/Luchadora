package cookiedragon.valuesystem

/**
 * @author cookiedragon234 16/Feb/2020
 */
class SelectableStringValue(name: String, vararg options: String): Value<String>(name, options[0]) {
	val options = listOf(*options)
}
