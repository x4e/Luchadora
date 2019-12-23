package cookiedragon.luchadora.module.impl.ui.gui.clickgui;

import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.impl.ui.gui.GuiManager;
import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.util.IRenderable;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.wrapper.java.Colour;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class CategoryElement implements IRenderable, Globals
{
	public final Category category;
	public boolean collapsed;
	public boolean dragging;
	public Vec2f position = new Vec2f(0,0);
	public Vec2f size = new Vec2f(0,0);
	public List<ModuleElement> modules = new ArrayList<>();
	
	public CategoryElement(Category category)
	{
		this.category = category;
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		float topX = position.x;
		float topY = position.y;
		float width = 100;
		
		float offset = mc.fontRenderer.getFontHeight() + 4;
		
		RenderUtils.renderRectangle(
			topX,
			topY,
			topX + width,
			topY + offset,
			Colour.BLACK.getRgb()
		);
		mc.fontRenderer.drawCenteredString(category.displayName, topX + 2, topY + 2, topX + width, Colour.WHITE.getRgb());
		
		int height = 0;
		
		if (!collapsed)
		{
			for (ModuleElement module : modules)
			{
				height += module.getHeight();
			}
			
			RenderUtils.renderOutline(
				topX,
				topY + offset,
				topX + width,
				topY + offset + height,
				Colour.ORANGE.getRgb()
			);
			
			for (ModuleElement module : modules)
			{
				module.render(mousePos, new Vec2f(topX + 2, topY + offset + 2));
				offset += module.getHeight();
			}
		}
		
		size.x = 100;
		size.y = height;
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (GuiManager.INSTANCE.getClickGui().getMouseOver(mousePos) == this)
		{
			Vec2f header = new Vec2f(100, mc.fontRenderer.getFontHeight() + 4);
			// Header
			if (position.contains(header, mousePos))
			{
				if (mouseID == 0)
				{
					dragging = true;
					return true;
				}
				else if (mouseID == 1)
				{
					collapsed = !collapsed;
					return true;
				}
			}
			else if (position.add(header).contains(size.subtract(header), mousePos))
			{
				for (ModuleElement module : modules)
				{
					if (module.position.contains(module.size, mousePos))
					{
						if (module.mouseClick(mousePos, mouseID))
							break;
					}
				}
			}
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
