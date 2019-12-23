package cookiedragon.luchadora.module;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.luchadora.ModuleInitialisationEvent;
import cookiedragon.luchadora.module.impl.combat.CrystalAuraModule;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.CategoryElement;
import cookiedragon.luchadora.module.impl.ui.elements.WatermarkElement;
import cookiedragon.luchadora.module.impl.ui.gui.GuiModule;
import cookiedragon.luchadora.util.SimpleClassLoader;
import cookiedragon.luchadora.value.Value;

import java.util.*;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class ModuleManager
{
	private static final Map<AbstractModule, List<Value>> modules = new HashMap<>();
	
	public static void init()
	{
		EventDispatcher.dispatch(new ModuleInitialisationEvent.Pre());
		
		new SimpleClassLoader<AbstractModule>()
			.build(
				CrystalAuraModule.class,
				GuiModule.class,
				WatermarkElement.class
			)
			.initialise(
				module -> modules.put(module, module.getValues()),
				module -> {},
				(module, e) -> new RuntimeException("Failed to initialise module '" + module.getName() + "'", e)
			);
		
		{
			CategoryElement module;
			
			module = new CategoryElement(Category.UI);
			modules.put(module, module.getValues());
			module = new CategoryElement(Category.COMBAT);
			modules.put(module, module.getValues());
		}
		
		System.out.println(modules.toString());
		
		EventDispatcher.dispatch(new ModuleInitialisationEvent.Post());
	}
	
	public static AbstractModule getModule(Class<? extends AbstractModule> moduleClazz)
	{
		for (AbstractModule module : modules.keySet())
		{
			if (moduleClazz.isAssignableFrom(module.getClass()))
				return module;
		}
		throw new RuntimeException("AbstractModule " + moduleClazz + " was not initialised!");
	}
	
	public static Set<AbstractModule> getModules()
	{
		return modules.keySet();
	}
	
	public static Map<AbstractModule, List<Value>> getAll()
	{
		return modules;
	}
	
	public static List<Value> getValuesForModule(AbstractModule module)
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
}
