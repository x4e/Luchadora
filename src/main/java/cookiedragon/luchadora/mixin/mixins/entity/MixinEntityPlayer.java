package cookiedragon.luchadora.mixin.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author cookiedragon234 14/Jan/2020
 */
@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer
{
	@Accessor("speedInAir")
	public abstract void setSpeedInAir(float speedInAir);
}
