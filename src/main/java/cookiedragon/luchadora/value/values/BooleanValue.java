package cookiedragon.luchadora.value.values;

import cookiedragon.luchadora.value.Value;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author cookiedragon234 06/Dec/2019
 */
public class BooleanValue extends Value<Boolean>
{
	public BooleanValue(String name, boolean defaultValue)
	{
		super(name, defaultValue);
	}
	
	@Override
	public JsonObject addToObject(JsonObject jsonObject)
	{
		jsonObject.addProperty(this.getName(), this.getValue());
		return jsonObject;
	}
	
	@Override
	public void retrieveFromObject(JsonObject jsonObject)
	{
		if (jsonObject.has(this.getName()))
		{
			JsonElement element = jsonObject.get(this.getName());
			
			try
			{
				this.setValue(element.getAsBoolean());
			}
			catch(Exception ignored){}
		}
	}
}
