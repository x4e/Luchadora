package cookiedragon.luchadora.event.entity

import net.minecraft.client.multiplayer.PlayerControllerMP

/**
 * @author cookiedragon234 17/Feb/2020
 */
data class EntityReachEvent(val playerController: PlayerControllerMP, var reachDistance: Float)
