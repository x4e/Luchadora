package cookiedragon.luchadora.module.impl.dev;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.value.values.EnumValue;
import net.minecraft.network.play.client.CPacketPlayer;

/**
 * @author cookiedragon234 31/Dec/2019
 */
@AbstractModule.Deceleration(name = "Invalid Teleport", description = "Attempts to teleport to invalid positions, causing unpredicted server actions", category = Category.DEV)
public class InvalidTeleportModule extends AbstractModule
{
	private enum Mode
	{
		NAN(),
		INFINITY(),
		NEG_INFINITY(),
		NULL(),
		MIN(),
		MAX()
	}
	
	private final EnumValue<Mode> modeVal = new EnumValue<>("Mode", Mode.NAN);
	
	@Override
	protected void onEnabled()
	{
		if(modeVal.getValue() == Mode.NAN)
		{
			sendPos(Double.NaN);
		}
		else if(modeVal.getValue() == Mode.INFINITY)
		{
			sendPos(Double.POSITIVE_INFINITY);
		}
		else if(modeVal.getValue() == Mode.NEG_INFINITY)
		{
			sendPos(Double.NEGATIVE_INFINITY);
		}
		else if(modeVal.getValue() == Mode.NULL)
		{
			sendPos(null);
		}
		else if(modeVal.getValue() == Mode.MIN)
		{
			sendPos(Double.MIN_VALUE);
		}
		else if(modeVal.getValue() == Mode.MAX)
		{
			sendPos(Double.MAX_VALUE);
		}
	}
	
	private static void sendPos(Double pos)
	{
		mc.connection.sendPacket(new CPacketPlayer.Position(pos, pos, pos, mc.player.onGround()));
	}
	
	@Override
	protected void onDisabled()
	{
	
	}
}
