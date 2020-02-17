package cookiedragon.valuesystem

import cookiedragon.luchadora.util.Key

/**
 * @author cookiedragon234 16/Feb/2020
 */
class KeyValue(name: String, defaultVal: Key): Value<Key>(name, defaultVal) {
	constructor(name: String): this(name, Key.KEY_NONE)
	constructor(name: String, defaultVal: String): this(name, Key.fromName(defaultVal))
	constructor(name: String, defaultVal: Int): this(name, Key.fromCode(defaultVal))
	
	fun isKeyDown() = this.value.isKeyDown
	fun hasChangedState() = this.value.hasChangedState
	fun hasBeenPressed() = this.value.hasBeenPressed
}
