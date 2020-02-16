package cookiedragon.luchadora.module;

import com.google.common.reflect.ClassPath;
import cookiedragon.eventsystem.EventDispatcher;
import cookiedragon.luchadora.event.luchadora.ModuleInitialisationEvent;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.CategoryElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.GuiModule;
import cookiedragon.valuesystem.Value;
import net.minecraft.launchwrapper.Launch;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class ModuleManager
{
	private static final Map<AbstractModule, List<Value<?>>> modules = new HashMap<>();
	
	@SuppressWarnings("UnstableApiUsage")
	public static void init()
	{
		EventDispatcher.Companion.dispatch(new ModuleInitialisationEvent.Pre());
		
		try
		{
			for (ClassPath.ClassInfo classInfo : ClassPath.from(Launch.classLoader).getAllClasses())
			{
				if (classInfo.getName().startsWith("cookiedragon.luchadora.module"))
				{
					try
					{
						Class<?> aClass = classInfo.load();
						if (!Modifier.isAbstract(aClass.getModifiers()) && AbstractModule.class.isAssignableFrom(aClass))
						{
							System.out.println("Found " + classInfo.getName());
							for (Constructor<?> constructor : aClass.getConstructors())
							{
								if (constructor.getParameterCount() == 0)
								{
									AbstractModule instance = (AbstractModule)constructor.newInstance();
									modules.put(instance, instance.getValues());
									break;
								}
							}
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Failure loading modules", e);
		}
		
		{
			GuiModule guiModule = getModule(GuiModule.class);
			
			for (Category category : Category.class.getEnumConstants())
			{
				CategoryElement module = new CategoryElement(category, guiModule);
				modules.put(module, module.getValues());
			}
		}
		
		System.out.println(modules.toString());
		
		EventDispatcher.Companion.dispatch(new ModuleInitialisationEvent.Post());
		System.out.println("Post module init");
	}
	
	public static <T extends AbstractModule> T getModule(Class<T> moduleClazz)
	{
		for (AbstractModule module : modules.keySet())
		{
			if (moduleClazz.isAssignableFrom(module.getClass()))
				return Objects.requireNonNull(moduleClazz.cast(module));
		}
		throw new RuntimeException("AbstractModule " + moduleClazz + " was not initialised!");
	}
	
	public static Set<AbstractModule> getModules()
	{
		return modules.keySet();
	}
	
	public static Map<AbstractModule, List<Value<?>>> getAll()
	{
		return modules;
	}
	
	public static List<Value<?>> getValuesForModule(AbstractModule module)
	{
		return modules.get(module);
	}
	
	public static Value findValueForModule(AbstractModule module, String valueName)
	{
		for (Value value : modules.get(module))
		{
			if (value.getName().equalsIgnoreCase(valueName))
				return value;
		}
		throw new RuntimeException("Couldnt find value '" + valueName + "' for module '" + module.getName() + "'");
	}
	
	public static Iterator<AbstractModule> iterator()
	{
		return modules.keySet().iterator();
	}
}
