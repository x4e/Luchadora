package cookiedragon.luchadora.util;

import com.google.gson.JsonObject;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public interface ISerializable
{
	JsonObject addToObject(JsonObject jsonObject);
	void retrieveFromObject(JsonObject jsonObject);
}
