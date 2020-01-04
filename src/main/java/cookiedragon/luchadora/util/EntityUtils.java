package cookiedragon.luchadora.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author cookiedragon234 02/Jan/2020
 */
public class EntityUtils
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	
	@Nullable
	public static RayTraceResult findMouseOver(Entity entity)
	{
		if (entity == mc.player)
		{
			return mc.objectMouseOver;
		}
		
		if (entity != null)
		{
			if (mc.world != null)
			{
				double reachDistance = (double)mc.playerController.getBlockReachDistance() + 3;
				return entity.rayTrace(reachDistance, mc.getRenderPartialTicks());
			}
		}
		return null;
	}
}
