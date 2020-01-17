package cookiedragon.luchadora.mixin.mixins.lwjgl;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.lwjgl.SetDisplayTitleEvent;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 12/Jan/2020
 */
@Mixin(Display.class)
public class MixinDisplay
{
	@Shadow
	private static String title;
	
	private static boolean bypass = false;
	
	@Inject(method = "setTitle", at = @At("HEAD"), cancellable = true, require = 1)
	private static void onSetTitle(String newTitle, CallbackInfo ci)
	{
		System.out.println("Set title " + newTitle + " : " + title);
		bypass = !bypass;
		if (!bypass)
		{
			return;
		}
		ci.cancel();
		SetDisplayTitleEvent event = new SetDisplayTitleEvent(title, newTitle);
		EventDispatcher.dispatch(event);
		if (!event.isCancelled())
			Display.setTitle(event.getNewTitle());
	}
}
