package cookiedragon.luchadora.module.impl.ui.gui.clickgui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.util.IRenderable;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.wrapper.java.Colour;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class ModuleElement implements Globals, IRenderable
{
	public final AbstractModule module;
	public Vec2f position;
	public Vec2f size;
	
	public ModuleElement(AbstractModule module)
	{
		this.module = module;
	}
	
	public int getHeight()
	{
		return mc.fontRenderer.getFontHeight() + 4;
	}
	
	public void render(Vec2f mousePos, Vec2f renderPos)
	{
		position = renderPos;
		size = new Vec2f(100, mc.fontRenderer.getFontHeight());
		mc.fontRenderer.drawCenteredString(module.getName(), renderPos.x, renderPos.y + 2, 100, Colour.WHITE.getRgb());
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
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
	
	// Not Used
	@Override
	public void render(Vec2f mousePos) {}
}
