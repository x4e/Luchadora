package cookiedragon.luchadora.util

import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderGlobal
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.AxisAlignedBB
import org.lwjgl.opengl.GL11
import java.awt.Color
import cookiedragon.luchadora.util.Globals.mc

/**
 * @author cookiedragon234 17/Feb/2020
 */
object RenderUtils {
	@JvmStatic
	fun renderOutline(left: Float, top: Float, right: Float, bottom: Float, colour: Int) {
		drawRect0(left, top, right, bottom, colour, GL11.GL_LINE_LOOP)
	}
	
	@JvmStatic
	fun renderRectangle(left: Float, top: Float, right: Float, bottom: Float, colour: Int) {
		drawRect0(left, top, right, bottom, colour, GL11.GL_QUADS)
	}
	
	@JvmStatic
	private fun drawRect0(left: Float, top: Float, right: Float, bottom: Float, colour: Int, glMode: Int) {
		val tessellator = Tessellator.getInstance()
		val bufferbuilder = tessellator.buffer
		GlStateManager.enableBlend()
		GlStateManager.disableTexture2D()
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
		colour(colour)
		bufferbuilder.begin(glMode, DefaultVertexFormats.POSITION)
		bufferbuilder.pos(left.toDouble(), bottom.toDouble(), 0.0).endVertex()
		bufferbuilder.pos(right.toDouble(), bottom.toDouble(), 0.0).endVertex()
		bufferbuilder.pos(right.toDouble(), top.toDouble(), 0.0).endVertex()
		bufferbuilder.pos(left.toDouble(), top.toDouble(), 0.0).endVertex()
		tessellator.draw()
		GlStateManager.enableTexture2D()
		GlStateManager.disableBlend()
	}
	
	@JvmStatic
	fun drawPixel(x: Float, y: Float, colour: Int) {
		val tessellator = Tessellator.getInstance()
		val bufferbuilder = tessellator.buffer
		GlStateManager.enableBlend()
		GlStateManager.disableTexture2D()
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
		colour(colour)
		bufferbuilder.begin(GL11.GL_POINTS, DefaultVertexFormats.POSITION)
		bufferbuilder.pos(x.toDouble(), y.toDouble(), 0.0).endVertex()
		tessellator.draw()
		GlStateManager.enableTexture2D()
		GlStateManager.disableBlend()
	}
	
	@JvmStatic
	fun colour(rgb: Int) {
		val r = (rgb shr 16 and 255).toFloat() / 255.0f
		val g = (rgb shr 8 and 255).toFloat() / 255.0f
		val b = (rgb and 255).toFloat() / 255.0f
		val a = (rgb shr 24 and 255).toFloat() / 255.0f
		GlStateManager.color(r, g, b, a)
	}
	
	@JvmStatic
	fun drawTexturedModalRect(x: Int, y: Int, textureX: Int, textureY: Int, width: Int, height: Int) {
		val f = 0.00390625f
		val f1 = 0.00390625f
		val tessellator = Tessellator.getInstance()
		val bufferbuilder = tessellator.buffer
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX)
		bufferbuilder.pos((x + 0).toDouble(), (y + height).toDouble(), 0.0).tex(((textureX + 0).toFloat() * 0.00390625f).toDouble(), ((textureY + height).toFloat() * 0.00390625f).toDouble()).endVertex()
		bufferbuilder.pos((x + width).toDouble(), (y + height).toDouble(), 0.0).tex(((textureX + width).toFloat() * 0.00390625f).toDouble(), ((textureY + height).toFloat() * 0.00390625f).toDouble()).endVertex()
		bufferbuilder.pos((x + width).toDouble(), (y + 0).toDouble(), 0.0).tex(((textureX + width).toFloat() * 0.00390625f).toDouble(), ((textureY + 0).toFloat() * 0.00390625f).toDouble()).endVertex()
		bufferbuilder.pos((x + 0).toDouble(), (y + 0).toDouble(), 0.0).tex(((textureX + 0).toFloat() * 0.00390625f).toDouble(), ((textureY + 0).toFloat() * 0.00390625f).toDouble()).endVertex()
		tessellator.draw()
	}
	
	@JvmStatic
	fun glColor(color: Color) {
		GlStateManager.color(color.red / 255.toFloat(), color.green / 255.toFloat(), color.blue / 255.toFloat(), color.alpha / 255.toFloat())
	}
	
	@JvmStatic
	fun drawSelectionBox(bb: AxisAlignedBB, color: Color) {
		GlStateManager.enableBlend()
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO)
		GlStateManager.disableTexture2D()
		GlStateManager.depthMask(false)
		val partialTicks: Float = mc.renderPartialTicks
		val d3: Double = mc.player.lastTickPosX + (mc.player.posX - mc.player.lastTickPosX) * partialTicks.toDouble()
		val d4: Double = mc.player.lastTickPosY + (mc.player.posY - mc.player.lastTickPosY) * partialTicks.toDouble()
		val d5: Double = mc.player.lastTickPosZ + (mc.player.posZ - mc.player.lastTickPosZ) * partialTicks.toDouble()
		RenderGlobal.drawSelectionBoundingBox(bb.grow(0.0020000000949949026).offset(-d3, -d4, -d5), color.red / 255f, color.green / 255f, color.blue / 255f, color.alpha / 255f)
		GlStateManager.depthMask(true)
		GlStateManager.enableTexture2D()
		GlStateManager.disableBlend()
	}
	
	@JvmStatic
	fun drawTextureAt(x: Int, y: Int, width: Int, height: Int, resourceLocation: ResourceLocation) {
		mc.textureManager.bindTexture(resourceLocation)
		GlStateManager.enableBlend()
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0f, 0f, width, height, width.toFloat(), height.toFloat())
		GlStateManager.disableBlend()
	}
}
