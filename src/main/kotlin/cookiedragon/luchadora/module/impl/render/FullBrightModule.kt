package cookiedragon.luchadora.module.impl.render

import cookiedragon.luchadora.event.api.Subscriber
import cookiedragon.luchadora.event.client.UpdateLightmapEvent
import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.Globals.mc
import cookiedragon.luchadora.value.values.NumberValue

/**
 * @author cookiedragon234 11/Jan/2020
 */
@AbstractModule.Declaration(name = "Full Bright", description = "Get rid of darkness", category = Category.RENDER)
class FullBrightModule : AbstractModule() {
	private val brightness = NumberValue("Brightness", 1f, 0, 3)
			.addCallback { oldVal, newVal -> updateLightmap(newVal.toFloat()) }
	
	@Subscriber
	private fun onUpdateLightmap(event: UpdateLightmapEvent) = updateLightmap(brightness.value.toFloat())
	
	override fun onEnabled() = updateLightmap(brightness.value.toFloat())
	
	override fun onDisabled() = mc.world.provider.generateLightBrightnessTable()
	
	private fun updateLightmap(brightness: Float) {
		if (mc.world.nonNull()) {
			for (i in 0 until mc.world.provider.lightBrightnessTable.size) {
				mc.world.provider.lightBrightnessTable[i] = brightness
			}
		}
	}
}
