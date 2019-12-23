package cookiedragon.luchadora.mixin.mixins;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.client.KeyPressedEvent;
import cookiedragon.luchadora.util.Key;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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
}
