package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.gui.FontRenderer;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class FontRendererWrapper
{
	private final FontRenderer fontRenderer;
	
	public FontRendererWrapper(FontRenderer fontRenderer)
	{
		this.fontRenderer = fontRenderer;
	}
	
	public int getFontHeight()
	{
		return fontRenderer.FONT_HEIGHT;
	}
	
	public int getStringWidth(String text)
	{
		return fontRenderer.getStringWidth(text);
	}
	
	public int drawStringWithShadow(String text, float x, float y, int color)
	{
		return fontRenderer.drawStringWithShadow(text, x, y, color);
	}
	
	public int drawString(String text, int x, int y, int color)
	{
		return fontRenderer.drawString(text, x, y, color);
	}
	
	public int drawString(String text, float x, float y, int color)
	{
		return fontRenderer.drawString(text, x, y, color, false);
	}
	
	public int drawString(String text, float x, float y, int color, boolean dropShadow)
	{
		return fontRenderer.drawString(text, x, y, color, dropShadow);
	}
	
	public int drawCenteredString(String text, float x, float y, float right, int color)
	{
		float newX = x + ((right - getStringWidth(text)) / 2f);
		return drawString(text, newX, y, color, false);
	}
}
