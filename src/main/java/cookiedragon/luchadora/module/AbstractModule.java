package cookiedragon.luchadora.module;

import com.google.gson.JsonObject;
import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.util.ISerializable;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.value.Value;
import cookiedragon.luchadora.value.values.BooleanValue;
import cookiedragon.luchadora.value.values.KeyValue;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public abstract class AbstractModule implements ISerializable, Globals
{
	private String name;
	private final String description;
	private final Category category;
	
	protected final Value<Boolean> enabled = new BooleanValue("Enabled", true);
	protected final Value<Boolean> visible = new BooleanValue("Visible", true);
	protected final Value<Key> keyBind = new KeyValue("Keybind", Key.KEY_NONE);
	
	public AbstractModule()
	{
		Declaration annotation = this.getAnnotation();
		this.name = annotation.name();
		this.description = annotation.description();
		this.category = annotation.category();
		this.enabled.setValue(annotation.defaultOn());
		this.visible.setValue(annotation.defaultVisible() && category.visible);
		this.keyBind.setValue(annotation.defaultBind());
		
		EventDispatcher.register(this);
		
		this.enabled.setDescription(this.description);
		this.enabled.addCallback((oldValue, newValue) ->
		{
			if (newValue)
			{
				EventDispatcher.subscribe(this);
				this.onEnabled();
			}
			else
			{
				EventDispatcher.unsubscribe(this);
				this.onDisabled();
			}
		});
	}
	
	protected void onEnabled(){}
	protected void onDisabled(){}
	
	public String getName()
	{
		return name;
	}
	
	protected void setName(String name)
	{
		this.name = name;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public boolean toggle()
	{
		getEnabled().setValue(!getEnabled().getValue());
		return getEnabled().getValue();
	}
	
	public void setEnabled(boolean enabled)
	{
		getEnabled().setValue(enabled);
	}
	
	public Value<Boolean> getEnabled()
	{
		return enabled;
	}
	
	public boolean isEnabled()
	{
		return enabled.getValue();
	}
	
	public Value<Boolean> getVisible()
	{
		return visible;
	}
	
	public Value<Key> getKeyBind()
	{
		return keyBind;
	}
	
	
	
	
	
	
	@Override
	public String toString()
	{
		return this.name;
	}
	
	private Declaration getAnnotation()
	{
		if (this.getClass().isAnnotationPresent(Declaration.class))
		{
			return this.getClass().getAnnotation(Declaration.class);
		}
		throw new RuntimeException("Annotation missing for module " + this.getClass().getName());
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface Declaration
	{
		@Nonnull String name();
		@Nonnull String description();
		@Nonnull Category category();
		
		boolean defaultOn() default false;
		boolean defaultVisible() default false;
		Key defaultBind() default Key.KEY_NONE;
	}
	
	public List<Value> getValues()
	{
		List<Value> values = new ArrayList<>();
		try
		{
			List<Class> clazzes = new ArrayList<>();
			
			Class clazz = this.getClass();
			do
			{
				clazzes.add(clazz);
			}
			while ((clazz = clazz.getSuperclass()) != null && AbstractModule.class.isAssignableFrom(clazz));
			
			for (int i = clazzes.size() - 1; i >= 0; i--)
			{
				clazz = clazzes.get(i);
				for (Field declaredField : clazz.getDeclaredFields())
				{
					if (Value.class.isAssignableFrom(declaredField.getType()))
					{
						declaredField.setAccessible(true);
						Value value = Objects.requireNonNull((Value)declaredField.get(this), "Error fetching value via reflection");
						values.add(value);
					}
				}
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException("Error while enumerating module values", e);
		}
		return values;
	}
	
	@Override
	public JsonObject addToObject(JsonObject jsonObject)
	{
		JsonObject moduleObj = new JsonObject();
		for (Value value : ModuleManager.getValuesForModule(this))
		{
			value.addToObject(moduleObj);
		}
		jsonObject.add(this.getName().toLowerCase(), moduleObj);
		return jsonObject;
	}
	
	@Override
	public void retrieveFromObject(JsonObject jsonObject)
	{
		try
		{
			JsonObject moduleObj = jsonObject.getAsJsonObject(this.getName().toLowerCase());
			for (Value value : ModuleManager.getValuesForModule(this))
			{
				value.retrieveFromObject(moduleObj);
			}
		}
		catch(Exception ignored){}
	}
}
