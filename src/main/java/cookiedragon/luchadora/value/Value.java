package cookiedragon.luchadora.value;

import cookiedragon.luchadora.util.ISerializable;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * @author cookiedragon234 06/Dec/2019
 */
public abstract class Value<T> implements ISerializable
{
	protected final String name;
	protected String description;
	protected final T defaultVal;
	protected T value;
	protected BiConsumer<T, T> callback; // called when the value of this value is set using the setValue function
	
	public Value(String name, T defaultVal)
	{
		this.name = name;
		this.value = this.defaultVal = defaultVal;
	}
	
	/**
	 * @param callback old value, new value
	 */
	public void addCallback(BiConsumer<T, T> callback)
	{
		this.callback = callback;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public Value<T> setDescription(String description)
	{
		this.description = description;
		return this;
	}
	
	public T getDefaultVal()
	{
		return defaultVal;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public void setValue(T value)
	{
		setValue(value, false);
	}
	
	public void setValue(T value, boolean bypassCallback)
	{
		T oldVal = this.value;
		this.value = value;
		
		if (!bypassCallback && callback != null)
			callback.accept(oldVal, value);
	}
	
	@Override
	public String toString()
	{
		return String.format("%s:%s:%s", this.name, this.value.getClass().getName(), this.value);
	}
}
