package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.EditHudGui;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.valuesystem.KeyValue;

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
			isListening = false;
			return;
		}
		
		size = new Vec2f(
			100,
			mc.fontRenderer.FONT_HEIGHT + 2
		);
		
		position.x += 2;
		size.x -= 2;
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			new Color(0,0,0, 50).getRGB()
		);
		
		ExtensionsKt.drawStringClamped(
			mc.fontRenderer,
			this.value.getName(),
			position.x + 1,
			position.y + 1,
			size.x / 2,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
		
		ExtensionsKt.drawStringRightClamped(
			mc.fontRenderer,
			isListening ? "..." : this.value.getValue().toString(),
			position.x + size.x,
			position.y + 1,
			size.x / 2,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
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
			else if (mouseID == 1)
			{
				isListening = false;
				return true;
			}
		}
		isListening = false;
		return false;
	}
	
	@Override
	public boolean keyTyped(Key key)
	{
		if (this.isListening && mc.currentScreen instanceof EditHudGui && !this.moduleElement.collapsed)
		{
			if (key != Key.KEY_ESCAPE)
			{
				this.value.setValue(key);
			}
			this.isListening = false;
			return true;
		}
		return false;
	}
}
