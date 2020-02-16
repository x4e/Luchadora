package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.valuesystem.Value;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class EnumValueElement extends ValueElement<Value<Enum<?>>>
{
	public EnumValueElement(Value<Enum<?>> value, ModuleElement categoryElement)
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
			this.value.getValue().toString(),
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
			ArrayList<Enum<?>> options = new ArrayList<Enum<?>>(EnumSet.allOf(value.getValue().getClass()));
			int currentIndex = options.indexOf(value.getValue());
			int size = options.size();
			
			int add;
			if (mouseID == 0)
				add = 1;
			else if (mouseID == 1)
				add = -1;
			else
				return false;
			
			int newIndex = currentIndex + add;
			
			if (newIndex < 0)
				newIndex = size - 1;
			else if (newIndex >= size)
				newIndex = 0;
			
			value.setValue(options.get(newIndex));
			
			return true;
		}
		
		return false;
	}
}
