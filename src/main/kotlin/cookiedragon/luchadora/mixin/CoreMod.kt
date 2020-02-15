package cookiedragon.luchadora.mixin

import net.minecraft.launchwrapper.Launch
import net.minecraft.launchwrapper.LaunchClassLoader
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin
import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

/**
 * @author cookiedragon234 15/Feb/2020
 */
class CoreMod: IFMLLoadingPlugin {
	init {
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
