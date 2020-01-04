package cookiedragon.luchadora.util;

/**
 * @author cookiedragon234 02/Jan/2020
 */
public class MovementUtils implements Globals
{
	public static double getDirection()
	{
		float rotationYaw = mc.player.getRotationYaw();
		
		float forward;
		if (mc.player.getMoveForward() < 0F)
		{
			rotationYaw += 180;
			forward = -0.5f;
		}
		else if (mc.player.getMoveForward() > 0F)
			forward = 0.5F;
		else
			forward = 1;
		
		if (mc.player.getMoveStrafing() > 0F)
			rotationYaw -= 90F * forward;
		else if (mc.player.getMoveStrafing() < 0F)
			rotationYaw += 90F * forward;
		
		return Math.toRadians(rotationYaw);
	}
	
	public static boolean isMoving()
	{
		return
			(
				mc.player.movementInput.getMoveForward() != 0
				&&
				mc.player.movementInput.getMoveStrafe() != 0
			);
	}
}
