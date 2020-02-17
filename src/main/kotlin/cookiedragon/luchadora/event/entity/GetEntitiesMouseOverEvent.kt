package cookiedragon.luchadora.event.entity

import net.minecraft.entity.Entity

/**
 * @author cookiedragon234 17/Feb/2020
 */
data class GetEntitiesMouseOverEvent(val entities: List<Entity>) {
	var size: Int = entities.size
}
