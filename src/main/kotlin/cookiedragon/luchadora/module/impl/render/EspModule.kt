package cookiedragon.luchadora.module.impl.render

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.entity.RenderEntityEvent
import cookiedragon.luchadora.event.entity.RenderEntityModelEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.OutlineUtils
import cookiedragon.luchadora.util.RenderUtils
import cookiedragon.luchadora.value.values.BooleanValue
import cookiedragon.luchadora.value.values.ColourValue
import cookiedragon.luchadora.value.values.EnumValue
import cookiedragon.luchadora.value.values.NumberValue
import net.minecraft.client.renderer.GlStateManager
import org.lwjgl.opengl.GL11
import java.awt.Color

/**
 * @author cookiedragon234 13/Jan/2020
 */
class EspModule: AbstractModule("Esp", "See entities through walls", Category.RENDER) {
	private val mode = EnumValue("Mode", Type.WIREFRAME)
	private val colour = ColourValue("Colour", Color.RED)
	private val width = NumberValue("Width", 2f, 0.1, 5)
	private val chams = BooleanValue("Chams", false)
	private val onTop = BooleanValue("On Top", true)
	
	private enum class Type {
		WIREFRAME,
		SOLID,
		OUTLINE
	}
	
	private var fancyGraphics: Boolean? = null
	private var gamma: Float? = null
	
	@Subscriber
	private fun onRenderEntity(entityModelEvent: RenderEntityModelEvent.Pre) {
		// TODO: We can add different player alphas here
		
		fancyGraphics = mc.gameSettings.fancyGraphics
		mc.gameSettings.fancyGraphics = false
		
		gamma = mc.gameSettings.gammaSetting
		mc.gameSettings.gammaSetting = 10000f
		
		if (onTop.value) {
			// Render the base model that we draw on top of
			entityModelEvent.modelBase.render(
					entityModelEvent.entity, entityModelEvent.limbSwing, entityModelEvent.limbSwingAmount, entityModelEvent.age, entityModelEvent.headYaw, entityModelEvent.headPitch, entityModelEvent.scale
			)
		}
		if (mode.value == Type.OUTLINE) {
			OutlineUtils.renderOne(width.value.toFloat())
			entityModelEvent.modelBase.render(
					entityModelEvent.entity, entityModelEvent.limbSwing, entityModelEvent.limbSwingAmount, entityModelEvent.age, entityModelEvent.headYaw, entityModelEvent.headPitch, entityModelEvent.scale
			)
			GlStateManager.glLineWidth(width.value.toFloat())
			OutlineUtils.renderTwo()
			entityModelEvent.modelBase.render(
					entityModelEvent.entity, entityModelEvent.limbSwing, entityModelEvent.limbSwingAmount, entityModelEvent.age, entityModelEvent.headYaw, entityModelEvent.headPitch, entityModelEvent.scale
			)
			GlStateManager.glLineWidth(width.value.toFloat())
			OutlineUtils.renderThree()
			OutlineUtils.renderFour(colour.value)
			entityModelEvent.modelBase.render(
					entityModelEvent.entity, entityModelEvent.limbSwing, entityModelEvent.limbSwingAmount, entityModelEvent.age, entityModelEvent.headYaw, entityModelEvent.headPitch, entityModelEvent.scale
			)
			GlStateManager.glLineWidth(width.value.toFloat())
			OutlineUtils.renderFive()
		} else {
			GL11.glPushMatrix()
			GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
			when (mode.value) {
				Type.WIREFRAME -> GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE)
				Type.SOLID -> GL11.glPolygonMode(GL11.GL_FRONT, GL11.GL_LINE)
			}
			
			GL11.glDisable(GL11.GL_TEXTURE_2D)
			GL11.glDisable(GL11.GL_LIGHTING)
			GL11.glDisable(GL11.GL_DEPTH_TEST)
			
			GL11.glEnable(GL11.GL_LINE_SMOOTH)
			GL11.glEnable(GL11.GL_BLEND)
			
			GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)
			RenderUtils.glColor(colour.value)
			GlStateManager.glLineWidth(width.value.toFloat())
			
			entityModelEvent.modelBase.render(
					entityModelEvent.entity, entityModelEvent.limbSwing, entityModelEvent.limbSwingAmount, entityModelEvent.age, entityModelEvent.headYaw, entityModelEvent.headPitch, entityModelEvent.scale
			)
			
			GL11.glPopAttrib()
			GL11.glPopMatrix()
		}
		if (!onTop.value) {
			// Render the base model that we draw on top of
			entityModelEvent.modelBase.render(
					entityModelEvent.entity, entityModelEvent.limbSwing, entityModelEvent.limbSwingAmount, entityModelEvent.age, entityModelEvent.headYaw, entityModelEvent.headPitch, entityModelEvent.scale
			)
		}
		mc.gameSettings.fancyGraphics = fancyGraphics!!
		mc.gameSettings.gammaSetting = gamma!!
		entityModelEvent.isCancelled = true
	}
	
	@Subscriber
	private fun renderEntityPre(event: RenderEntityEvent.Pre) {
		if (chams.value) {
			GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL)
			GL11.glPolygonOffset(1f, -100000f)
		}
	}
	
	@Subscriber
	private fun renderEntityPost(event: RenderEntityEvent.Post) {
		if (chams.value) {
			GL11.glPolygonOffset(1f, 100000f)
			GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL)
		}
	}
}
