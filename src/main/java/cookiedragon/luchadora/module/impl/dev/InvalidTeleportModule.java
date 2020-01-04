package cookiedragon.luchadora.module.impl.dev;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.util.ChatUtils;
import cookiedragon.luchadora.value.values.EnumValue;
import net.minecraft.network.play.client.CPacketPlayer;

import java.text.MessageFormat;

/**
 * @author cookiedragon234 31/Dec/2019
 */
@AbstractModule.Declaration(name = "Invalid Teleport", description = "Attempts to teleport to invalid positions, causing unpredicted server actions", category = Category.DEV)
public class InvalidTeleportModule extends AbstractModule
{
	private static Double lastPos;
	
	private enum Mode
	{
		NAN(),
		INFINITY(),
		NEG_INFINITY(),
		ZERO(),
		MIN(),
		MAX()
	}
	
	private final EnumValue<Mode> modeVal = new EnumValue<>("Mode", Mode.NAN);
	
	@Override
	protected void onEnabled()
	{
		if (mc.isSinglePlayer())
		{
			ChatUtils.sendMessage("Cant use in SP");
			setEnabled(false);
			return;
		}
		
		if (modeVal.getValue() == Mode.NAN)
		{
			sendPos(Double.NaN);
		}
		else if (modeVal.getValue() == Mode.INFINITY)
		{
			sendPos(Double.POSITIVE_INFINITY);
		}
		else if (modeVal.getValue() == Mode.NEG_INFINITY)
		{
			sendPos(Double.NEGATIVE_INFINITY);
		}
		else if (modeVal.getValue() == Mode.ZERO)
		{
			sendPos(0d);
		}
		else if (modeVal.getValue() == Mode.MIN)
		{
			sendPos(Double.MIN_VALUE);
		}
		else if (modeVal.getValue() == Mode.MAX)
		{
			sendPos(Double.MAX_VALUE);
		}
		ChatUtils.sendMessage(MessageFormat.format("Sent Position [{0}]", Double.toHexString(lastPos)));
		this.setEnabled(false);
	}
	
	private static void sendPos(Double pos)
	{
		lastPos = pos;
		mc.connection.sendPacket(new CPacketPlayer.Position(pos, pos, pos, mc.player.onGround()));
	}
	
	@Override
	protected void onDisabled()
	{
	
	}
}
