package cookiedragon.luchadora.mixin.mixins.world

import net.minecraft.client.renderer.DestroyBlockProgress
import net.minecraft.client.renderer.RenderGlobal
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(RenderGlobal::class)
interface IMixinRenderGlobal {
	@Accessor(value = "damagedBlocks")
	fun getDamagedBlocks(): Map<Int?, DestroyBlockProgress?>?
}
