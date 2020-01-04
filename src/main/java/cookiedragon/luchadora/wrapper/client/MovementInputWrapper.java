package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;

/**
 * @author cookiedragon234 02/Jan/2020
 */
public class MovementInputWrapper
{
	private final Minecraft mc;
	
	public MovementInputWrapper(Minecraft mc)
	{
		this.mc = mc;
	}
	
	public float getMoveForward()
	{
		return mc.player.movementInput.moveForward;
	}
	
	public float getMoveStrafe()
	{
		return mc.player.movementInput.moveStrafe;
	}
}
