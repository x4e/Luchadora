package cookiedragon.luchadora.mixin.mixins.netty

import net.minecraft.client.network.NetHandlerPlayClient
import net.minecraft.client.network.NetworkPlayerInfo
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow
import java.util.*

/**
 * @author cookiedragon234 18/Feb/2020
 */
@Mixin(NetHandlerPlayClient::class)
abstract class MixinNetHandlerPlayClient {
	@JvmField
	@Shadow
	var playerInfoMap: Map<UUID, NetworkPlayerInfo>? = null
}
