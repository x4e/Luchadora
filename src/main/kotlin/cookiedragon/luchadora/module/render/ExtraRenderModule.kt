package cookiedragon.luchadora.module.render

import cookiedragon.eventsystem.Subscriber
import cookiedragon.luchadora.event.block.GetBlockModelEvent
import cookiedragon.luchadora.event.block.GetBlockRenderLayerEvent
import cookiedragon.luchadora.event.block.GetBlockRenderTypeEvent
import cookiedragon.luchadora.event.client.UpdateLightmapEvent
import cookiedragon.luchadora.event.render.RenderBarrierParticleEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.valuesystem.NumberValue
import cookiedragon.valuesystem.Value
import net.minecraft.client.renderer.block.model.IBakedModel
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumBlockRenderType

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
	private val barriers = Value("Barriers", true)
			.addCallback { oldVal, newVal -> mc.renderGlobal.loadRenderers() }
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
	private val noFog = Value("No Fog", true)
	
	
	
	
	
	/* ----- FULL BRIGHT ----- */
	private val fullBright = Value("Full Bright", true)
			.addCallback { oldVal, newVal -> fullBrightUpdate()}
	private val brightness = NumberValue("Brightness", 1f, 0f, 3f)
			.addCallback { oldVal, newVal -> if (fullBright.value){updateLightmap(newVal)} }
	
	private fun fullBrightUpdate() {
		if (fullBright.value) {
			updateLightmap(brightness.value)
		} else {
			regenerateDefaultLightmap()
		}
	}
	
	@Subscriber
	private fun onUpdateLightmap(event: UpdateLightmapEvent) = fullBrightUpdate()
	
	private fun updateLightmap(brightness: Float) {
		if (mc.world != null) {
			with (mc.world.provider.lightBrightnessTable) {
				for (i in indices) {
					set(i, brightness)
				}
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
