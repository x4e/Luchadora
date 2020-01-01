package cookiedragon.luchadora.mixin.mixins.entity;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.client.Render2dEvent;
import cookiedragon.luchadora.event.client.UpdateLightmapEvent;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 21/Dec/2019
 */
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer
{
	@Inject(
		method = "updateCameraAndRender",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V",
			shift = At.Shift.AFTER // Get called after the render game overlay function
		)
	)
	private void gameOverlayWrapper(float partialTicks, long nanoTime, CallbackInfo ci)
	{
		Render2dEvent event = new Render2dEvent();
		EventDispatcher.dispatch(event);
	}
	
	@Inject(method = "updateLightmap", at = @At(value = "HEAD", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V"))
	private void updateLightmapWrapper(float partialTicks, CallbackInfo ci)
	{
		EventDispatcher.dispatch(new UpdateLightmapEvent());
	}
}
