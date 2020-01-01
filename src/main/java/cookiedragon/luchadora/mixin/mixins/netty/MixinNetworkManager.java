package cookiedragon.luchadora.mixin.mixins.netty;

import io.netty.channel.Channel;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author cookiedragon234 22/Dec/2019
 */
@Mixin(NetworkManager.class)
public class MixinNetworkManager
{
	@Shadow
	private Channel channel;
	
	@Redirect(method = "handleDisconnection", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/INetHandler;onDisconnect(Lnet/minecraft/util/text/ITextComponent;)V"), remap = false)
	private void onDisconnectNetwork(INetHandler iNetHandler, ITextComponent reason)
	{
		System.out.println(channel.getClass().toString());
		
		if (!channel.isActive())
			iNetHandler.onDisconnect(new TextComponentString("Channel was not active"));
		
		if (!channel.isOpen())
			iNetHandler.onDisconnect(new TextComponentString("Channel was not open"));
	}
}
