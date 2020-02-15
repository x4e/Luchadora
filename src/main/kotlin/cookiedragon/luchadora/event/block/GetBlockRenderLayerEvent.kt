package cookiedragon.luchadora.event.block

import net.minecraft.block.Block
import net.minecraft.util.BlockRenderLayer

/**
 * @author cookiedragon234 14/Jan/2020
 */
data class GetBlockRenderLayerEvent(val block: Block, var renderLayer: BlockRenderLayer)
