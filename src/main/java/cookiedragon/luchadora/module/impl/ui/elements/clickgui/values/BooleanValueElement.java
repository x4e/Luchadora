package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.BooleanValue;

import java.awt.*;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class BooleanValueElement extends ValueElement<BooleanValue>
{
	private boolean isEnabledValue;
	
	public BooleanValueElement(BooleanValue value, ModuleElement categoryElement)
	{
		super(value, categoryElement);
		isEnabledValue = value.getName().equals("Enabled");
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		if (moduleElement.collapsed && !isEnabledValue)
		{
			size = new Vec2f(0,0);
			return;
		}
		
		size = new Vec2f(
			100,
			mc.fontRenderer.getFontHeight() + 2
		);
		
		if (!isEnabledValue)
		{
			position.x += 2;
			size.x -= 2;
		}
		
		Color onColour = isEnabledValue ?
			moduleElement.categoryElement.guiModule.secondaryColour.getValue()
			:
			moduleElement.categoryElement.guiModule.tertiaryColour.getValue();
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			value.getValue() ? onColour.getRGB() : moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			new Color(0,0,0, 50).getRGB()
		);
		
		mc.fontRenderer.drawString(
			isEnabledValue ? this.moduleElement.module.getName() : this.value.getName(),
			position.x + 1.5f,
			position.y + 1.5f,
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
				this.value.setValue(!this.value.getValue());
				return true;
			}
			else if (mouseID == 1 && isEnabledValue)
			{
				this.moduleElement.collapsed = !this.moduleElement.collapsed;
				return true;
			}
		}
		return false;
	}
}
