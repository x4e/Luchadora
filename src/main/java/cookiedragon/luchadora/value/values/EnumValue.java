package cookiedragon.luchadora.value.values;

import cookiedragon.luchadora.value.Value;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cookiedragon234 06/Dec/2019
 */
@SuppressWarnings("unchecked")
public class EnumValue<T extends Enum> extends Value<Enum>
{
	protected final Class<? extends Enum> enumType;
	protected final Set<? extends Enum> options;
	
	public EnumValue(String name, T defaultValue)
	{
		super(
			name,
			defaultValue
		);
		
		this.enumType = defaultValue.getClass();
		options = Arrays.stream(defaultValue.getClass().getEnumConstants())
			.collect(Collectors.toCollection(HashSet::new));
	}
	
	@Override
	public JsonObject addToObject(JsonObject jsonObject)
	{
		return null;
	}
	
	@Override
	public void retrieveFromObject(JsonObject jsonObject)
	{
	
	}
}
