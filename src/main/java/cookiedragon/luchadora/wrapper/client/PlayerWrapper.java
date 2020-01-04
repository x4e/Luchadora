package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.text.ITextComponent;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class PlayerWrapper
{
	private final Minecraft mc;
	public final MovementInputWrapper movementInput;
	
	public PlayerWrapper(Minecraft mc)
	{
		this.mc = mc;
		this.movementInput = new MovementInputWrapper(mc);
	}
	
	public void sendMessage(ITextComponent component)
	{
		mc.player.sendMessage(component);
	}
	
	public boolean onGround()
	{
		return mc.player.onGround;
	}
	
	public boolean elytraEquipped()
	{
		return mc.player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == Items.ELYTRA;
	}
	
	public boolean isElytraFlying()
	{
		return mc.player.isElytraFlying();
	}
	
	public void setVelocity(double x, double y, double z)
	{
		mc.player.setVelocity(x, y, z);
	}
	
	public void setJumpMovementFactor(float movementFactor)
	{
		mc.player.jumpMovementFactor = movementFactor;
	}
	
	public int getTicksExisted()
	{
		return mc.player.ticksExisted;
	}
	
	public void setMotionY(double motionY)
	{
		mc.player.motionY = motionY;
	}
	
	public float getRotationYaw()
	{
		return mc.player.rotationYaw;
	}
	
	public float getRotationPitch()
	{
		return mc.player.rotationPitch;
	}
	
	public float getMoveForward()
	{
		return mc.player.moveForward;
	}
	
	public float getMoveStrafing()
	{
		return mc.player.moveStrafing;
	}
}
