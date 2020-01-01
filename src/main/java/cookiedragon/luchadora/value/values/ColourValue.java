package cookiedragon.luchadora.value.values;

import com.google.gson.JsonObject;
import cookiedragon.luchadora.value.Value;

import java.awt.*;

/**
 * @author cookiedragon234 26/Dec/2019
 */
public class ColourValue extends Value<Color>
{
	public ColourValue(String name, Color defaultVal)
	{
		super(name, defaultVal);
	}
	
	@Override
	public JsonObject addToObject(JsonObject jsonObject)
	{
		JsonObject rgbObj = new JsonObject();
		rgbObj.addProperty("r", this.value.getRed());
		rgbObj.addProperty("g", this.value.getGreen());
		rgbObj.addProperty("b", this.value.getBlue());
		rgbObj.addProperty("a", this.value.getAlpha());
		
		jsonObject.add(this.getName(), rgbObj);
		return jsonObject;
	}
	
	@Override
	public void retrieveFromObject(JsonObject jsonObject)
	{
		try
		{
			JsonObject rgbObj = jsonObject.getAsJsonObject(this.getName());
			this.setValue(
				new Color(
					rgbObj.get("r").getAsInt(),
					rgbObj.get("g").getAsInt(),
					rgbObj.get("b").getAsInt(),
					rgbObj.get("a").getAsInt()
				)
			);
		}
		catch (Exception ignored){}
	}
}
