package cookiedragon.luchadora.mixin.mixins.entity;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.client.Render2dEvent;
import cookiedragon.luchadora.event.client.Render3dEvent;
import cookiedragon.luchadora.event.client.UpdateLightmapEvent;
import cookiedragon.luchadora.event.entity.EntityTurnEvent;
import cookiedragon.luchadora.event.entity.GetEntitiesMouseOverEvent;
import cookiedragon.luchadora.managers.PerspectiveManager;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

/**
 * @author cookiedragon234 21/Dec/2019
 */
@Mixin(EntityRenderer.class)
public class MixinEntityRenderer
{
	@Inject(
		method = "updateCameraAndRender", at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V",
			shift = At.Shift.AFTER // Get called after the render game overlay function
		),
		require = 1
	)
	private void gameOverlayWrapper(float partialTicks, long nanoTime, CallbackInfo ci)
	{
		Render2dEvent event = new Render2dEvent();
		EventDispatcher.dispatch(event);
	}
	
	@Inject(method = "updateLightmap", at = @At(value = "HEAD", target = "Lnet/minecraft/client/renderer/texture/DynamicTexture;updateDynamicTexture()V"), require = 1)
	private void updateLightmapWrapper(float partialTicks, CallbackInfo ci)
	{
		EventDispatcher.dispatch(new UpdateLightmapEvent());
	}
	
	@Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationYaw:F"), require = 5)
	private float getRotationYawWrapper(Entity entity)
	{
		return PerspectiveManager.getYaw();
	}
	
	@Redirect(method = "orientCamera", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/Entity;rotationPitch:F"), require = 4)
	private float getRotationPitchWrapper(Entity entity)
	{
		return PerspectiveManager.getPitch();
	}
	
	@Redirect(method = "getMouseOver", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", remap = false), require = 1)
	private int getNumEntities(List list)
	{
		GetEntitiesMouseOverEvent event = new GetEntitiesMouseOverEvent(Objects.requireNonNull((List<Entity>)list));
		EventDispatcher.dispatch(event);
		return event.size;
	}
	
	@Redirect(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;turn(FF)V"), require = 1)
	private void onTurnPlayerWrapper(EntityPlayerSP playerSP, float yaw, float pitch)
	{
		EntityTurnEvent.Pre event = new EntityTurnEvent.Pre(playerSP, yaw, pitch);
		EventDispatcher.dispatch(event);
		if (!event.isCancelled())
		{
			event.getEntity().turn(event.getYaw(), event.getPitch());
			
			EntityTurnEvent.Post secondEvent = new EntityTurnEvent.Post(event.getEntity(), event.getYaw(), event.getPitch());
			EventDispatcher.dispatch(event);
		}
	}
	
	/*@Inject(method = "renderWorldPass", at = @At("HEAD"), cancellable = true, require = 1)
	private void renderWorldHead(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci)
	{
		Render3dEvent.Pre event = new Render3dEvent.Pre();
		EventDispatcher.dispatch(event);
		if(event.isCancelled())
			ci.cancel();
	}
	
	@Inject(method = "renderWorldPass", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z"), require = 1)
	private void renderWorldPost(int pass, float partialTicks, long finishTimeNano, CallbackInfo ci)
	{
		Render3dEvent.Post event = new Render3dEvent.Post();
		EventDispatcher.dispatch(event);
	}*/
}
