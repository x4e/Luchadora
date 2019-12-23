package cookiedragon.luchadora.util;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class RenderUtils
{
	public static void renderOutline(float left, float top, float right, float bottom, int colour)
	{
		drawRect0(left, top, right, bottom, colour, GL11.GL_LINE_LOOP);
	}
	
	public static void renderRectangle(float left, float top, float right, float bottom, int colour)
	{
		drawRect0(left, top, right, bottom, colour, GL11.GL_QUADS);
	}
	
	private static void drawRect0(float left, float top, float right, float bottom, int colour, int glMode)
	{
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		colour(colour);
		bufferbuilder.begin(glMode, DefaultVertexFormats.POSITION);
		bufferbuilder.pos((double)left, (double)bottom, 0.0D).endVertex();
		bufferbuilder.pos((double)right, (double)bottom, 0.0D).endVertex();
		bufferbuilder.pos((double)right, (double)top, 0.0D).endVertex();
		bufferbuilder.pos((double)left, (double)top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void colour(int rgb)
	{
		float r = (float)(rgb >> 16 & 255) / 255.0F;
		float g = (float)(rgb >> 8 & 255) / 255.0F;
		float b = (float)(rgb & 255) / 255.0F;
		float a = (float)(rgb >> 24 & 255) / 255.0F;
		GlStateManager.color(r, g, b, a);
	}
}
