package cookiedragon.luchadora.value.values;

import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.value.Value;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;

/**
 * @author cookiedragon234 06/Dec/2019
 */
public class KeyValue extends Value<Key>
{
	public KeyValue(String name)
	{
		this(name, Keyboard.KEY_NONE);
	}
	
	public KeyValue(String name, String defaultValue)
	{
		this(name, Key.fromName(defaultValue));
	}
	
	public KeyValue(String name, int defaultValue)
	{
		this(name, Key.fromCode(defaultValue));
	}
	
	public KeyValue(String name, Key defaultKey)
	{
		super(name, defaultKey);
	}
	
	public boolean isKeyDown()
	{
		return this.getValue().isKeyDown();
	}
	
	public boolean hasChangedState()
	{
		return this.getValue().hasChangedState();
	}
	
	@Nullable
	public Boolean hasBeenPressed()
	{
		return this.getValue().hasBeenPressed();
	}
	
	@Override
	public JsonObject addToObject(JsonObject jsonObject)
	{
		jsonObject.addProperty(this.getName(), this.getValue().name());
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
				this.setValue(Key.fromName(element.getAsString()));
			}
			catch(Exception ignored){}
		}
	}
}
