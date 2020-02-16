package cookiedragon.luchadora.module.impl.ui.elements.clickgui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.ModuleManager;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.values.*;
import cookiedragon.luchadora.util.*;
import cookiedragon.valuesystem.KeyValue;
import cookiedragon.valuesystem.NumberValue;
import cookiedragon.valuesystem.SelectableStringValue;
import cookiedragon.valuesystem.Value;;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class ModuleElement implements IRenderable, Globals
{
	public final CategoryElement categoryElement;
	Vec2f modulePosition = new Vec2f(0,0);
	Vec2f moduleSize = new Vec2f(0,0);
	public final AbstractModule module;
	private final List<ValueElement> valueElements;
	public boolean collapsed = true;
	
	public ModuleElement(AbstractModule module, CategoryElement categoryElement)
	{
		this.module = module;
		this.categoryElement = categoryElement;
		valueElements = new ArrayList<>();
		
		for (Value<?> value : ModuleManager.getValuesForModule(module))
		{
			if (value.getValue() instanceof Boolean)
			{
				valueElements.add(new BooleanValueElement((Value<Boolean>) value, this));
			}
			else if (value.getValue() instanceof Enum)
			{
				valueElements.add(new EnumValueElement((Value<Enum<?>>) value, this));
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
			else if (value.getValue() instanceof String)
			{
				valueElements.add(new StringValueElement((Value<String>) value, this));
			}
			else if (value.getValue() instanceof Color)
			{
				valueElements.add(new ColourValueElement((Value<Color>) value, this));
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
	
	public boolean keyTyped(Key key)
	{
		for (ValueElement valueElement : valueElements)
		{
			if (valueElement.keyTyped(key))
			{
				return true;
			}
		}
		return false;
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
			if (valueElement.mouseClick(mousePos, mouseID))
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		for (ValueElement valueElement : valueElements)
		{
			valueElement.mouseRelease(mousePos, mouseID);
		}
		
		return false;
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		for (ValueElement valueElement : valueElements)
		{
			valueElement.mouseClickMove(mousePos, mouseID);
		}
		
		return false;
	}
}
