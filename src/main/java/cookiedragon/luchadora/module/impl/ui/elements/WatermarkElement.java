package cookiedragon.luchadora.module.impl.ui.elements;

import cookiedragon.luchadora.Luchadora;
import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.impl.ui.AbstractHudElement;

import java.awt.*;

/**
 * @author cookiedragon234 21/Dec/2019
 */
public class WatermarkElement extends AbstractHudElement
{
	public WatermarkElement()
	{
		super("Watermark", "", Category.UI);
	}
	@Override
	public void render(Vec2f mousePos)
	{
		super.render(mousePos);
		if (!shouldRender()) return;
		
		String text = Luchadora.getBrand();
		
		ExtensionsKt.drawString(mc.fontRenderer, text, position.x, position.y, Color.ORANGE.getRGB());
		
		size.x = mc.fontRenderer.getStringWidth(text);
		size.y = mc.fontRenderer.FONT_HEIGHT;
	}
}
