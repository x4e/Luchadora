package cookiedragon.luchadora.mixin

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

/**
 * @author cookiedragon234 15/Feb/2020
 */
class CoreMod: IFMLLoadingPlugin {
	init {
		MixinBootstrap.init()
		Mixins.addConfiguration("mixins.luchadora.json")
	}
	
	override fun getASMTransformerClass(): Array<String?>? = arrayOfNulls(0)
	override fun getModContainerClass(): String? = null
	override fun getSetupClass(): String? = null
	override fun injectData(data: Map<String?, Any?>?) = Unit
	override fun getAccessTransformerClass(): String? = null
}
