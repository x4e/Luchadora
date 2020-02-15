package cookiedragon.luchadora.mixin.mixins.entity

import net.minecraft.entity.player.EntityPlayer
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(EntityPlayer::class)
abstract class MixinEntityPlayer {
	@Accessor("speedInAir")
	abstract fun setSpeedInAir(speedInAir: Float)
}
