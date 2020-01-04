package cookiedragon.luchadora.guis;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.io.IOException;

/**
 * @author cookiedragon234 31/Dec/2019
 */
public class CustomGuiGameOver extends GuiGameOver
{
	public CustomGuiGameOver(GuiGameOver old)
	{
		super(getCauseOfDeath(old));
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		
		this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 120, "[LD] Exit Screen"));
		
		for (GuiButton guiButton : buttonList)
		{
			guiButton.enabled = true;
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if (button.id == 3)
		{
			mc.displayGuiScreen(null);
			mc.player.isDead = false;
			mc.player.setHealth(20);
			this.onGuiClosed();
			return;
		}
		super.actionPerformed(button);
	}
	
	private static ITextComponent getCauseOfDeath(GuiGameOver screen)
	{
		return (ITextComponent) ObfuscationReflectionHelper.getPrivateValue(
			GuiGameOver.class,
			screen,
			"field_184871_f"
		);
	}
}
