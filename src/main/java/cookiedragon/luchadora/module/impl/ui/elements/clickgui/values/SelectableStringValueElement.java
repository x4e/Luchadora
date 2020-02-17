package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.valuesystem.SelectableStringValue;

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
			this.value.getValue(),
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
			
			int currentIndex = value.getOptions().indexOf(value.getValue());
			
			try
			{
				value.setValue(
					Objects.requireNonNull(
						value.getOptions().get(currentIndex + 1)
					), false
				);
			}
			catch (Exception e)
			{
				value.setValue(value.getOptions().get(0), false);
			}
			
			return true;
		}
		
		return false;
	}
}
