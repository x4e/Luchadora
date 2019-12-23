package cookiedragon.luchadora.module.impl.ui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.util.*;
import org.lwjgl.opengl.Display;

import java.awt.*;

/**
 * @author cookiedragon234 21/Dec/2019
 */
public abstract class AbstractHudElement extends AbstractModule implements IRenderable, Globals
{
	public Vec2f position = new Vec2f(0,0);
	public Vec2f size = new Vec2f(0,0);
	public Vec2f draggableSize = null;
	
	private boolean dragging = false;
	private Vec2f mouseOffset = new Vec2f(0,0);
	
	public AbstractHudElement()
	{
		super();
		HudManager.hudElements.add(this);
	}
	
	public void keyTyped(Key key)
	{ }
	
	@Override
	public void render(Vec2f mousePos)
	{
		forceInView();
		
		if (shouldRender() && mc.getCurrentScreen() instanceof EditHudGui)
		{
			if (HudManager.getMouseOver(mousePos) == this || dragging)
			{
				RenderUtils.renderRectangle(
					position.x - 1,
					position.y - 1,
					position.x + size.x + 1,
					position.y + size.y + 1,
					Color.DARK_GRAY.getRGB()
				);
			}
			else
			{
				/*RenderUtils.renderRectangle(
					position.x - 1,
					position.y - 1,
					position.x + size.x + 1,
					position.y + size.y + 1,
					Color.GRAY.getRGB()
				);*/
			}
		}
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (HudManager.getMouseOver(mousePos) == this)
		{
			if (mouseID == 0)
			{
				dragging = true;
				mouseOffset.x = mousePos.x - position.x;
				mouseOffset.y = mousePos.y - position.y;
				
				HudManager.bringToFront(this);
				
				return true; // Event Consumed
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		if (mouseID == 0 && dragging)
		{
			dragging = false;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		if (mouseID == 0 && dragging)
		{
			position.x = mousePos.x - mouseOffset.x;
			position.y = mousePos.y - mouseOffset.y;
			forceInView();
			
			return true;
		}
		return false;
	}
	
	
	
	
	
	public boolean shouldRender()
	{
		return getEnabled().getValue();
	}
	
	protected void forceInView()
	{
		if (position.x < 0)
		{
			position.x = 0;
		}
		if (position.y < 0)
		{
			position.y = 0;
		}
		
		int width = Display.getWidth();
		int height = Display.getHeight();
		
		if (position.x + size.x > width)
		{
			position.x = width - size.x;
		}
		if (position.y + size.y > height)
		{
			position.y = height - size.y;
		}
	}
	
	public boolean isMouseOver(Vec2f mousePos)
	{
		Vec2f size = draggableSize == null ? this.size : this.draggableSize;
		
		return (
			mousePos.x >= position.x
			&&
			mousePos.y >= position.y
			&&
			mousePos.x <= position.x + size.x
			&&
			mousePos.y <= position.y + size.y
		);
	}
	
	@Override
	protected void onEnabled()
	{ }
	
	@Override
	protected void onDisabled()
	{ }
}
