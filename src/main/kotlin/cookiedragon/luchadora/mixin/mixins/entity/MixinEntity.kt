package cookiedragon.luchadora.mixin.mixins.entity

import net.minecraft.entity.Entity
import net.minecraft.world.World
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow

/**
 * @author cookiedragon234 15/Feb/2020
 */
@Mixin(Entity::class)
abstract class MixinEntity {
	@Shadow
	var world: World? = null
}
