package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
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
	
	
	public InventoryPlayer getInventory()
	{
		return mc.player.inventory;
	}
	
	public BlockPos getPosition()
	{
		return mc.player.getPosition();
	}
	
	
	public double getDistance(double x, double y, double z)
	{
		return mc.player.getDistance(x, y, z);
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
	
	public void turn(float yaw, float pitch)
	{
		mc.player.turn(yaw, pitch);
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
	
	public void setRotationYaw(float yaw)
	{
		mc.player.rotationYaw = yaw;
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
	
	public Entity getRidingEntity()
	{
		return mc.player.getRidingEntity();
	}
	
	public void setRotation(float yaw, float pitch)
	{
		mc.player.setPositionAndRotation(mc.player.posX, mc.player.posY, mc.player.posZ, yaw, pitch);
	}
}
