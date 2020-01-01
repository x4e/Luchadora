package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.SelectableStringValue;

import java.awt.*;
import java.util.Objects;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class SelectableStringValueElement extends ValueElement<SelectableStringValue>
{
	public SelectableStringValueElement(SelectableStringValue value, ModuleElement categoryElement)
	{
		super(value, categoryElement);
	}
	
	@SuppressWarnings("Duplicates")
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
			moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			Color.BLACK.getRGB()
		);
		
		mc.fontRenderer.drawStringClamped(
			this.value.getName(),
			position.x + 1,
			position.y + 1,
			size.x / 2,
			Color.LIGHT_GRAY.getRGB()
		);
		
		mc.fontRenderer.drawStringRightClamped(
			this.value.getValue(),
			position.x + size.x,
			position.y + 1,
			size.x / 2,
			Color.LIGHT_GRAY.getRGB()
		);
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if(position.contains(size, mousePos))
		{
			
			int currentIndex = value.options.indexOf(value.getValue());
			
			try
			{
				value.setValue(
					Objects.requireNonNull(
						value.options.get(currentIndex + 1)
					)
				);
			}
			catch (Exception e)
			{
				value.setValue(value.options.get(0));
			}
			
			return true;
		}
		
		return false;
	}
}
