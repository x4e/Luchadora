package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class FontRendererWrapper
{
	private final FontRenderer fontRenderer;
	
	public FontRendererWrapper(Minecraft mc)
	{
		this.fontRenderer = mc.fontRenderer;
	}
	
	public FontRenderer getFontRenderer()
	{
		return fontRenderer;
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
	
	public int drawStringClamped(String text, float x, float y, float width, int color)
	{
		int original = fontRenderer.FONT_HEIGHT;
		
		int stringWidth = getStringWidth(text);
		
		if(stringWidth > width)
		{
			float scale = width / stringWidth;
			GlStateManager.scale(scale, scale, 1);
			
			int out = drawString(text, x / scale, y / scale, color);
			
			GlStateManager.scale(1/scale, 1/scale, 1);
			return out;
		}
		else
		{
			return drawString(text, x, y, color);
		}
	}
	
	public int drawStringRightClamped(String text, float x, float y, float width, int color)
	{
		int original = fontRenderer.FONT_HEIGHT;
		
		int stringWidth = getStringWidth(text);
		
		if(stringWidth > width)
		{
			float scale = width / stringWidth;
			GlStateManager.scale(scale, scale, 1);
			
			x = x / scale;
			y = y / scale;
			
			float pos = x - getStringWidth(text);
			int out = drawString(text, pos, y, color);
			
			GlStateManager.scale(1/scale, 1/scale, 1);
			return out;
		}
		else
		{
			float pos = x - getStringWidth(text);
			return drawString(text, pos, y, color);
		}
	}
	
	public int drawStringRight(String text, float x, float y, float right, int color)
	{
		float pos = right - getStringWidth(text);
		return drawString(text, pos, y, color);
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
