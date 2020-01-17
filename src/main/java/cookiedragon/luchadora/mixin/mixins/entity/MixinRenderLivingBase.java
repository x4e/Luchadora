package cookiedragon.luchadora.mixin.mixins.entity;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.entity.RenderEntityEvent;
import cookiedragon.luchadora.event.entity.RenderEntityModelEvent;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 13/Jan/2020
 */
@Mixin(RenderLivingBase.class)
public abstract class MixinRenderLivingBase
{
	@Redirect(
		method = "renderModel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"
		)
	)
	private void renderModelWrapper(ModelBase modelBase, Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		{
			RenderEntityModelEvent.Pre event = new RenderEntityModelEvent.Pre(modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			EventDispatcher.dispatch(event);
			if (event.isCancelled()) return;
			
			modelBase.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		}
		{
			RenderEntityModelEvent.Post event = new RenderEntityModelEvent.Post(modelBase, entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
			EventDispatcher.dispatch(event);
		}
	}
	
	@Inject(method = "doRender", at = @At("HEAD"), cancellable = true, require = 1)
	private void doRenderHead(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci)
	{
		RenderEntityEvent.Pre event = new RenderEntityEvent.Pre(entity, x, y, z, entityYaw, partialTicks);
		EventDispatcher.dispatch(event);
	}
	
	@Inject(method = "doRender", at = @At("RETURN"), require = 1)
	private void doRenderReturn(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci)
	{
		RenderEntityEvent.Post event = new RenderEntityEvent.Post(entity, x, y, z, entityYaw, partialTicks);
		EventDispatcher.dispatch(event);
	}
}
