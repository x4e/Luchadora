package cookiedragon.luchadora.event.block

import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.IBakedModel

/**
 * @author cookiedragon234 14/Jan/2020
 */
data class GetBlockModelEvent(val state: IBlockState, var model: IBakedModel)
