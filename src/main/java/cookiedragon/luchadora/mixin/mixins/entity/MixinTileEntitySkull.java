package cookiedragon.luchadora.mixin.mixins.entity;

import com.mojang.authlib.ProfileLookupCallback;
import net.minecraft.server.management.PlayerProfileCache;
import net.minecraft.tileentity.TileEntitySkull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author cookiedragon234 18/Feb/2020
 */
@Mixin(TileEntitySkull.class)
public abstract class MixinTileEntitySkull {
	@Shadow
	public static PlayerProfileCache profileCache;
}
