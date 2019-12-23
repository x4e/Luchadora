package cookiedragon.luchadora.module.impl.ui.gui.clickgui;

import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.ModuleManager;
import cookiedragon.luchadora.util.IRenderable;
import cookiedragon.luchadora.util.Vec2f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class ClickGui implements IRenderable
{
	private final List<CategoryElement> elements;
	
	public ClickGui()
	{
		elements = new ArrayList<>();
		
		for (Category enumConstant : Category.class.getEnumConstants())
		{
			CategoryElement categoryElement = new CategoryElement(enumConstant);
			elements.add(categoryElement);
			
			for (AbstractModule module : ModuleManager.getModules())
			{
				if (module.getCategory() == enumConstant)
					categoryElement.modules.add(new ModuleElement(module));
			}
		}
	}
	
	public CategoryElement getMouseOver(Vec2f mousePos)
	{
		CategoryElement mouseOver = null;
		for (CategoryElement element : elements)
		{
			if (element.position.contains(element.size, mousePos))
				mouseOver = element;
		}
		return mouseOver;
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		for (CategoryElement element : elements)
		{
			element.render(mousePos);
		}
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		Iterator<CategoryElement> iterator = elements.iterator();
		while (iterator.hasNext() && !iterator.next().mouseClick(mousePos, mouseID)) {}
		return iterator.hasNext();
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		Iterator<CategoryElement> iterator = elements.iterator();
		while (iterator.hasNext() && !iterator.next().mouseRelease(mousePos, mouseID)) {}
		return iterator.hasNext();
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		Iterator<CategoryElement> iterator = elements.iterator();
		while (iterator.hasNext() && !iterator.next().mouseClickMove(mousePos, mouseID)) {}
		return iterator.hasNext();
	}
}
