package cookiedragon.luchadora.module.impl.ui.elements;

import cookiedragon.luchadora.Luchadora;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.impl.ui.AbstractHudElement;
import cookiedragon.luchadora.util.Vec2f;

import java.awt.*;

/**
 * @author cookiedragon234 21/Dec/2019
 */
@AbstractModule.Declaration(name = "Watermark", description = "", category = Category.UI)
public class WatermarkElement extends AbstractHudElement
{
	@Override
	public void render(Vec2f mousePos)
	{
		super.render(mousePos);
		if (!shouldRender()) return;
		
		String text = Luchadora.getBrand();
		mc.fontRenderer.drawString(text, position.x, position.y, Color.ORANGE.getRGB());
		
		size.x = mc.fontRenderer.getStringWidth(text);
		size.y = mc.fontRenderer.getFontHeight();
	}
}
