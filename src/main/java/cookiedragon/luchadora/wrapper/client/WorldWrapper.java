package cookiedragon.luchadora.wrapper.client;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;

/**
 * @author cookiedragon234 01/Jan/2020
 */
public class WorldWrapper
{
	private final Minecraft mc;
	public final WorldProviderWrapper provider;
	
	public WorldWrapper(Minecraft mc)
	{
		this.mc = mc;
		this.provider = new WorldProviderWrapper(mc);
	}
	
	public boolean nonNull()
	{
		return mc.world != null;
	}
	
	@Nullable
	public Entity getEntityByID(int id)
	{
		return mc.world.getEntityByID(id);
	}
	
	public IBlockState getBlockState(BlockPos pos)
	{
		return mc.world.getBlockState(pos);
	}
}
