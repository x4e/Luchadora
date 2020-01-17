package cookiedragon.luchadora.mixin.mixins.entity;

import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * @author cookiedragon234 14/Jan/2020
 */
@Mixin(EntityPlayer.class)
public class MixinEntityPlayer
{
	@Shadow protected float speedInAir;
	
	public void setSpeedInAir(float speed)
	{
		this.speedInAir = speed;
	}
}
