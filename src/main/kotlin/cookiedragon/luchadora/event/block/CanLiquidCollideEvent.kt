package cookiedragon.luchadora.event.block

import cookiedragon.luchadora.event.api.AbstractEvent
import net.minecraft.block.BlockLiquid
import net.minecraft.block.state.IBlockState

/**
 * @author cookiedragon234 13/Jan/2020
 */
data class CanLiquidCollideEvent(val block: BlockLiquid, val state: IBlockState, val hitIfLiquid: Boolean, var returnVal: Boolean): AbstractEvent()
