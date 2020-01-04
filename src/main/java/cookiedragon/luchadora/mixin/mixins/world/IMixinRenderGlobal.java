package cookiedragon.luchadora.mixin.mixins.world;

import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * @author cookiedragon234 04/Jan/2020
 */
@Mixin(RenderGlobal.class)
public interface IMixinRenderGlobal
{
	@Accessor(value = "damagedBlocks")
	Map<Integer, DestroyBlockProgress> getDamagedBlocks();
}
