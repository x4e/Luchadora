package cookiedragon.luchadora.value.values;

import com.google.gson.JsonElement;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.value.Value;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cookiedragon234 06/Dec/2019
 */
@SuppressWarnings("unchecked")
public class EnumValue<T extends Enum> extends Value<Enum>
{
	public final Class<? extends Enum> enumType;
	public final List<? extends Enum> options;
	
	public EnumValue(String name, T defaultValue)
	{
		super(
			name,
			defaultValue
		);
		
		this.enumType = defaultValue.getClass();
		options = Arrays.stream(defaultValue.getClass().getEnumConstants())
			.collect(Collectors.toCollection(ArrayList::new));
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
			
			for (Enum enumConstant : this.defaultVal.getClass().getEnumConstants())
			{
				if(enumConstant.name().equals(element.getAsString()))
					this.setValue(enumConstant);
			}
		}
	}
}
