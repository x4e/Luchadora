package cookiedragon.luchadora.module.impl.ui.elements.clickgui;

import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.util.IRenderable;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.Value;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public abstract class ValueElement<T extends Value> implements IRenderable, Globals
{
	protected final T value;
	protected final ModuleElement moduleElement;
	public Vec2f position = new Vec2f(0,0);
	public Vec2f size = new Vec2f(0,0);
	
	public ValueElement(T value, ModuleElement moduleElement)
	{
		this.value = value;
		this.moduleElement = moduleElement;
	}
	
	public boolean keyTyped(Key key)
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
}
