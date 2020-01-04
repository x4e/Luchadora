package cookiedragon.luchadora.util;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glVertex3d;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class RenderUtils
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
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
		float r = (float) (rgb >> 16 & 255) / 255.0F;
		float g = (float) (rgb >> 8 & 255) / 255.0F;
		float b = (float) (rgb & 255) / 255.0F;
		float a = (float) (rgb >> 24 & 255) / 255.0F;
		GlStateManager.color(r, g, b, a);
	}
	
	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
	{
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double)(x + 0), (double)(y + height), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double)(x + width), (double)(y + height), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double)(x + width), (double)(y + 0), 0).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double)(x + 0), (double)(y + 0), 0).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
		tessellator.draw();
	}
	
	public static void glColor(Color color)
	{
		GlStateManager.color(color.getRed() / 255, color.getGreen() / 255, color.getBlue() / 255, color.getAlpha() / 255);
	}
	
	public static void drawSelectionBox(AxisAlignedBB bb, Color color)
	{
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		
		float partialTicks = mc.getRenderPartialTicks();
		
		double d3 = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * (double)partialTicks;
		double d4 = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * (double)partialTicks;
		double d5 = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * (double)partialTicks;
		RenderGlobal.drawSelectionBoundingBox(bb.grow(0.0020000000949949026D).offset(-d3, -d4, -d5), color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
		
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
}
