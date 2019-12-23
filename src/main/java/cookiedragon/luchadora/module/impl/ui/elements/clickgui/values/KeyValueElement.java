package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.client.KeyPressedEvent;
import cookiedragon.luchadora.module.impl.ui.EditHudGui;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.KeyValue;

import java.awt.*;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class KeyValueElement extends ValueElement<KeyValue>
{
	private boolean isListening = false;
	
	public KeyValueElement(KeyValue value, ModuleElement categoryElement)
	{
		super(value, categoryElement);
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		if (moduleElement.collapsed)
		{
			size = new Vec2f(0,0);
			return;
		}
		
		size = new Vec2f(
			100,
			mc.fontRenderer.getFontHeight() + 2
		);
		
		position.x += 2;
		size.x -= 2;
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			Color.DARK_GRAY.getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			Color.BLACK.getRGB()
		);
		
		mc.fontRenderer.drawString(
			isListening ? "..." : this.value.getValue().name(),
			position.x + 1,
			position.y + 1,
			Color.LIGHT_GRAY.getRGB()
		);
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (position.contains(size, mousePos))
		{
			if (mouseID == 0)
			{
				isListening = !isListening;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void keyTyped(Key key)
	{
		if (this.isListening && mc.getCurrentScreen() instanceof EditHudGui && !this.moduleElement.collapsed)
		{
			this.value.setValue(key);
			this.isListening = false;
		}
	}
}
