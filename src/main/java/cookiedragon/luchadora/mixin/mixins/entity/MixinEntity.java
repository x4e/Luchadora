package cookiedragon.luchadora.mixin.mixins.entity;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author cookiedragon234 11/Jan/2020
 */
@Mixin(Entity.class)
public abstract class MixinEntity
{
	@Shadow
	public World world;
}
