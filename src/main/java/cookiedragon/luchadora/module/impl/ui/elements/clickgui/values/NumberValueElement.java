package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.EditHudGui;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.NumberValue;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class NumberValueElement extends ValueElement<NumberValue>
{
	private boolean isDragging = false;
	private Vec2f mouseOffset = new Vec2f(0,0);
	private Number cachedVal = null;
	
	public NumberValueElement(NumberValue value, ModuleElement categoryElement)
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
			mc.fontRenderer.FONT_HEIGHT + 2
		);
		
		position.x += 2;
		size.x -= 2;
		
		float max = value.max.floatValue();
		float min = value.min.floatValue();
		float val = cachedVal == null ? value.getValue().floatValue() : cachedVal.floatValue();
		
		
		
		float progress = ((val - min) / (max - min)) * size.x;
		
		RenderUtils.renderRectangle(
			position.x + progress,
			position.y,
			position.x + size.x,
			position.y + size.y,
			moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + progress,
			position.y + size.y,
			moduleElement.categoryElement.guiModule.tertiaryColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			new Color(0,0,0, 50).getRGB()
		);
		
		
		ExtensionsKt.drawString(
			mc.fontRenderer,
			this.value.getName(),
			position.x + 1,
			position.y + 1,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
		
		ExtensionsKt.drawStringRight(
			mc.fontRenderer,
			cachedVal == null ? this.value.getValue().toString() : cachedVal.toString(),
			position.x + 1,
			position.y + 1,
			position.x + size.x,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (position.contains(size, mousePos) && mouseID == 0)
		{
			this.isDragging = true;
			float progress = (mousePos.x - position.x) / size.x;
			float scale = value.max.floatValue() - value.min.floatValue();
			setValue(value.min.floatValue() + (scale * progress));
			return true;
		}
		this.isDragging = false;
		return false;
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		if (this.isDragging && mc.currentScreen instanceof EditHudGui && !this.moduleElement.collapsed)
		{
			if (mouseID == 0)
			{
				mousePos = mousePos.copy();
				mousePos.x = Math.max(mousePos.x, position.x);
				mousePos.x = Math.min(mousePos.x, position.x + size.x);
				
				float progress = (mousePos.x - position.x) / size.x;
				float scale = value.max.floatValue() - value.min.floatValue();
				setValue(value.min.floatValue() + (scale * progress));
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		if (cachedVal != null)
		{
			setValue0(cachedVal.floatValue());
			cachedVal = null;
			return true;
		}
		
		this.isDragging = false;
		return false;
	}
	private void setValue(float newVal)
	{
		if (value.slowUpdate)
		{
			cachedVal = round(newVal, 2);
			return;
		}
		setValue0(newVal);
	}
	
	private void setValue0(float newVal)
	{
		if (this.value.getValue() instanceof Integer)
		{
			this.value.setValue(Math.round(newVal));
		}
		else if (this.value.getValue() instanceof Float)
		{
			this.value.setValue((float) round(newVal, 2));
		}
		else if (this.value.getValue() instanceof Double)
		{
			this.value.setValue(round(newVal, 2));
		}
		else if (this.value.getValue() instanceof Long)
		{
			this.value.setValue((long) Math.round(newVal));
		}
	}
	
	private static double round(double value, int places)
	{
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
