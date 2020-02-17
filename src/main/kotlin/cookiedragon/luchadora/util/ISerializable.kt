package cookiedragon.luchadora.util

import com.google.gson.JsonObject

/**
 * @author cookiedragon234 17/Feb/2020
 */
interface ISerializable {
	fun addToObject(jsonObject: JsonObject): JsonObject
	fun retrieveFromObject(jsonObject: JsonObject)
}
