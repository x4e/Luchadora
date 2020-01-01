package cookiedragon.luchadora.mixin.mixins;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.client.KeyPressedEvent;
import cookiedragon.luchadora.event.entity.AllowInteractEvent;
import cookiedragon.luchadora.util.Key;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 15/Dec/2019
 */
@Mixin(Minecraft.class)
public class MixinMinecraft
{
	@Inject(method = "runTickKeyboard", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;dispatchKeypresses()V"))
	private void onKeyPressWrapper(CallbackInfo ci)
	{
		if (Keyboard.getEventKeyState())
		{
			EventDispatcher.dispatch(new KeyPressedEvent(Key.fromCode(Keyboard.getEventKey())));
		}
	}
	
	@Redirect(method = "sendClickBlockToController", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
	private boolean isHandActiveWrapper(EntityPlayerSP playerSP)
	{
		AllowInteractEvent event = new AllowInteractEvent(playerSP.isHandActive());
		EventDispatcher.dispatch(event);
		return event.isUsingItem;
	}
}
