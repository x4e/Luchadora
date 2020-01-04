package cookiedragon.luchadora.mixin.mixins;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.client.KeyPressedEvent;
import cookiedragon.luchadora.event.entity.AllowInteractEvent;
import cookiedragon.luchadora.util.Key;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author cookiedragon234 15/Dec/2019
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft
{
	@Shadow
	public GameSettings gameSettings;
	
	@Shadow
	protected abstract void clickMouse();
	
	@Shadow
	protected abstract void middleClickMouse();
	
	@Shadow
	protected abstract void rightClickMouse();
	
	@Shadow public EntityPlayerSP player;
	
	@Shadow public PlayerControllerMP playerController;
	
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
	
	@Redirect(method = "rightClickMouse", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0), require = 1)
	private boolean isHittingBlockWrapper(PlayerControllerMP playerControllerMP)
	{
		AllowInteractEvent event = new AllowInteractEvent(playerControllerMP.getIsHittingBlock());
		EventDispatcher.dispatch(event);
		return event.isUsingItem;
	}
}
