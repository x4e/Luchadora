package cookiedragon.luchadora.event.block

import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.util.EnumBlockRenderType

/**
 * @author cookiedragon234 14/Jan/2020
 */
data class GetBlockRenderTypeEvent(val block: Block, val state: IBlockState, var renderType: EnumBlockRenderType)
