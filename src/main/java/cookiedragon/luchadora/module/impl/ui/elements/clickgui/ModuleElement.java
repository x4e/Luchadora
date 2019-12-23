package cookiedragon.luchadora.module.impl.ui.elements.clickgui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.ModuleManager;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.values.*;
import cookiedragon.luchadora.util.*;
import cookiedragon.luchadora.value.Value;
import cookiedragon.luchadora.value.values.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class ModuleElement implements IRenderable, Globals
{
	Vec2f modulePosition = new Vec2f(0,0);
	Vec2f moduleSize = new Vec2f(0,0);
	public final AbstractModule module;
	private final List<ValueElement> valueElements;
	public boolean collapsed = true;
	
	public ModuleElement(AbstractModule module)
	{
		this.module = module;
		valueElements = new ArrayList<>();
		
		System.out.println(ModuleManager.getValuesForModule(module));
		for (Value value : ModuleManager.getValuesForModule(module))
		{
			if (value instanceof BooleanValue)
			{
				valueElements.add(new BooleanValueElement((BooleanValue) value, this));
			}
			else if (value instanceof EnumValue)
			{
				valueElements.add(new EnumValueElement((EnumValue) value, this));
			}
			else if (value instanceof KeyValue)
			{
				valueElements.add(new KeyValueElement((KeyValue) value, this));
			}
			else if (value instanceof NumberValue)
			{
				valueElements.add(new NumberValueElement((NumberValue) value, this));
			}
			else if (value instanceof SelectableStringValue)
			{
				valueElements.add(new SelectableStringValueElement((SelectableStringValue) value, this));
			}
			else if (value instanceof StringValue)
			{
				valueElements.add(new StringValueElement((StringValue) value, this));
			}
			else
			{
				new RuntimeException("Gui didnt recognise value " + value.getClass().toString()).printStackTrace();
			}
		}
	}
	
	public Category getCategory()
	{
		return module.getCategory();
	}
	
	public void keyTyped(Key key)
	{
		for (ValueElement valueElement : valueElements)
		{
			valueElement.keyTyped(key);
		}
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		moduleSize = new Vec2f(
			100,
			0
		);
		for (ValueElement valueElement : valueElements)
		{
			valueElement.position = modulePosition.copy();
			valueElement.position.y += moduleSize.y;
			
			valueElement.render(mousePos);
			
			moduleSize.y += valueElement.size.y;
		}
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		
		for (ValueElement valueElement : valueElements)
		{
			valueElement.mouseClick(mousePos, mouseID);
		}
		
		return false;
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		return false;
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		return false;
	}
}
