package cookiedragon.luchadora.event.block

import net.minecraft.block.BlockLiquid
import net.minecraft.block.state.IBlockState

/**
 * @author cookiedragon234 13/Jan/2020
 */
data class CanLiquidCollideEvent(var forceCollide: Boolean = false)
