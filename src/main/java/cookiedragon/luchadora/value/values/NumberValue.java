package cookiedragon.luchadora.value.values;

import cookiedragon.luchadora.value.Value;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author cookiedragon234 06/Dec/2019
 */
public class NumberValue extends Value<Number>
{
	public final Number min;
	public final Number max;
	public final boolean slowUpdate;
	
	public NumberValue(String name, Long value, Number min, Number max)
	{
		this(name, (Number) value, min.longValue(), max.longValue());
	}
	
	public NumberValue(String name, Integer value, Number min, Number max)
	{
		this(name, (Number) value, min.intValue(), max.intValue());
	}
	
	public NumberValue(String name, Short value, Number min, Number max)
	{
		this(name, (Number) value, min.shortValue(), max.shortValue());
	}
	
	public NumberValue(String name, Float value, Number min, Number max)
	{
		this(name, (Number) value, min.floatValue(), max.floatValue());
	}
	
	public NumberValue(String name, Double value, Number min, Number max)
	{
		this(name, (Number) value, min.doubleValue(), max.doubleValue());
	}
	
	public NumberValue(String name, Long value, Number min, Number max, boolean slowUpdate)
	{
		this(name, (Number) value, min.longValue(), max.longValue(), slowUpdate);
	}
	
	public NumberValue(String name, Integer value, Number min, Number max, boolean slowUpdate)
	{
		this(name, (Number) value, min.intValue(), max.intValue(), slowUpdate);
	}
	
	public NumberValue(String name, Short value, Number min, Number max, boolean slowUpdate)
	{
		this(name, (Number) value, min.shortValue(), max.shortValue(), slowUpdate);
	}
	
	public NumberValue(String name, Float value, Number min, Number max, boolean slowUpdate)
	{
		this(name, (Number) value, min.floatValue(), max.floatValue(), slowUpdate);
	}
	
	public NumberValue(String name, Double value, Number min, Number max, boolean slowUpdate)
	{
		this(name, (Number) value, min.doubleValue(), max.doubleValue(), slowUpdate);
	}
	
	private NumberValue(String name, Number value, Number min, Number max)
	{
		this(name, value, min, max, false);
	}
	
	private NumberValue(String name, Number value, Number min, Number max, boolean slowUpdate)
	{
		super(name, value);
		this.min = min;
		this.max = max;
		this.slowUpdate = slowUpdate;
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
				this.setValue(element.getAsNumber());
			}
			catch(Exception ignored){}
		}
	}
}
