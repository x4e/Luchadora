package cookiedragon.luchadora.mixin.mixins.netty;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author cookiedragon234 21/Feb/2020
 */
@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
	@Final
	@Shadow
	private Map<UUID, NetworkPlayerInfo> playerInfoMap;
	
	public HashMap<UUID, NetworkPlayerInfo> getPlayerInfoHashMap() {
		return (HashMap<UUID, NetworkPlayerInfo>) playerInfoMap;
	}
}
