package cookiedragon.luchadora.module.impl.ui.gui;

import cookiedragon.luchadora.module.impl.ui.gui.clickgui.ClickGui;
import cookiedragon.luchadora.util.IRenderable;
import cookiedragon.luchadora.util.Vec2f;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class GuiManager implements IRenderable
{
	public static final GuiManager INSTANCE = new GuiManager();
	public final List<IRenderable> renderables = new ArrayList<>();
	
	private GuiManager()
	{
		renderables.add(new ClickGui());
	}
	
	public ClickGui getClickGui()
	{
		for (IRenderable renderable : renderables)
		{
			if (renderable instanceof ClickGui)
				return (ClickGui) renderable;
		}
		return null;
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		for (IRenderable renderable : renderables)
		{
			renderable.render(mousePos);
		}
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		Iterator<IRenderable> iterator = renderables.iterator();
		while (iterator.hasNext() && !iterator.next().mouseClick(mousePos, mouseID)) {}
		return iterator.hasNext();
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		Iterator<IRenderable> iterator = renderables.iterator();
		while (iterator.hasNext() && !iterator.next().mouseRelease(mousePos, mouseID)) {}
		return iterator.hasNext();
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		Iterator<IRenderable> iterator = renderables.iterator();
		while (iterator.hasNext() && !iterator.next().mouseClickMove(mousePos, mouseID)) {}
		return iterator.hasNext();
	}
}
