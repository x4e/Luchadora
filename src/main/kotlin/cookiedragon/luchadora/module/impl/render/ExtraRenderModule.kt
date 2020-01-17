package cookiedragon.luchadora.module.impl.render

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.block.GetBlockModelEvent
import cookiedragon.luchadora.event.block.GetBlockRenderLayerEvent
import cookiedragon.luchadora.event.block.GetBlockRenderTypeEvent
import cookiedragon.luchadora.event.client.UpdateLightmapEvent
import cookiedragon.luchadora.event.render.RenderBarrierEvent
import cookiedragon.luchadora.event.render.RenderBarrierParticleEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.Globals
import cookiedragon.luchadora.value.values.BooleanValue
import cookiedragon.luchadora.value.values.NumberValue
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.block.model.IBakedModel
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType
import net.minecraft.util.EnumParticleTypes
import net.minecraft.util.math.BlockPos
import java.util.*

/**
 * @author cookiedragon234 12/Jan/2020
 */
class ExtraRenderModule: AbstractModule("Extra Render", "See things you dont normally", Category.RENDER) {
	override fun onEnabled() {
		fullBrightUpdate()
		mc.renderGlobal.loadRenderers()
	}
	
	override fun onDisabled() {
		fullBrightUpdate()
		mc.renderGlobal.loadRenderers()
	}
	
	
	/* ----- BARRIERS ----- */
	private val barriers = BooleanValue("Barriers", true)
			.addCallback {oldVal, newVal -> mc.renderGlobal.loadRenderers()}
	private val barrierModel: IBakedModel by lazy {
		mc.renderItem.itemModelMesher.getItemModel(ItemStack(Blocks.BARRIER))
	}
	
	@Subscriber
	private fun getBlockModel(event: GetBlockModelEvent) {
		if (barriers.value && event.state.block == Blocks.BARRIER) {
			event.model = barrierModel
		}
	}
	
	@Subscriber
	private fun getBlockRenderLayer(event: GetBlockRenderLayerEvent) {
		if (barriers.value && event.block == Blocks.BARRIER) {
			event.renderLayer = BlockRenderLayer.CUTOUT
		}
	}
	
	@Subscriber
	private fun getBlockRenderType(event: GetBlockRenderTypeEvent) {
		if (barriers.value && event.block == Blocks.BARRIER) {
			event.renderType = EnumBlockRenderType.MODEL
		}
	}
	
	@Subscriber
	private fun onRenderBarrierParticle(event: RenderBarrierParticleEvent) {
		if (barriers.value)
			event.isCancelled = true
	}
	
	
	/* ----- NO FOG ----- */
	private val noFog = BooleanValue("No Fog", true)
	
	
	
	
	
	/* ----- FULL BRIGHT ----- */
	private val fullBright = BooleanValue("Full Bright", true)
			.addCallback { oldVal, newVal -> fullBrightUpdate()}
	private val brightness = NumberValue("Brightness", 1f, 0, 3)
			.addCallback { oldVal, newVal -> if (fullBright.value){updateLightmap(newVal.toFloat())} }
	
	private fun fullBrightUpdate() {
		if (fullBright.value) {
			updateLightmap(brightness.value.toFloat())
		} else {
			regenerateDefaultLightmap()
		}
	}
	
	@Subscriber
	private fun onUpdateLightmap(event: UpdateLightmapEvent) = fullBrightUpdate()
	
	private fun updateLightmap(brightness: Float) {
		if (mc.world != null) {
			for (i in 0 until mc.world.provider.lightBrightnessTable.size) {
				mc.world.provider.lightBrightnessTable[i] = brightness
			}
		}
	}
	
	private fun regenerateDefaultLightmap() {
		val lightBrightnessTable = mc.world.provider.lightBrightnessTable
		
		for (i in 0..15) {
			val f1 = 1.0f - i.toFloat() / 15.0f
			lightBrightnessTable[i] = (1.0f - f1) / (f1 * 3.0f + 1.0f) * 1.0f + 0.0f
		}
	}
}
