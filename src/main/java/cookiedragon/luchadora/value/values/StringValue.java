package cookiedragon.luchadora.value.values;

import cookiedragon.luchadora.value.Value;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Objects;

/**
 * @author cookiedragon234 06/Dec/2019
 */
public class StringValue extends Value<String>
{
	public StringValue(String name, String defaultVal)
	{
		super(name, defaultVal);
	}
	
	@Override
	public String getValue()
	{
		return this.value.toString();
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
				this.setValue(Objects.requireNonNull(element.getAsString()));
			}
			catch(Exception ignored){}
		}
	}
}
