package cookiedragon.luchadora.mixin

import net.minecraft.launchwrapper.LogWrapper
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

/**
 * @author cookiedragon234 15/Feb/2020
 */
class CoreMod: IFMLLoadingPlugin {
	init {
		LogWrapper.retarget(LogManager.getLogger("Luchadora"))
		try {
			MixinBootstrap.init()
			Mixins.addConfiguration("mixins.luchadora.json")
		} catch (t: Throwable) {
			t.printStackTrace()
		}
	}
	
	override fun getASMTransformerClass(): Array<String?>? = arrayOfNulls(0)
	override fun getModContainerClass(): String? = null
	override fun getSetupClass(): String? = null
	override fun injectData(data: Map<String?, Any?>?) = Unit
	override fun getAccessTransformerClass(): String? = null
}
