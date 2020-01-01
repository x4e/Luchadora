package cookiedragon.luchadora.module.impl.ui.elements.clickgui;

import cookiedragon.luchadora.module.impl.ui.HudManager;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * @author cookiedragon234 21/Dec/2019
 */
public class EditHudGui extends GuiScreen
{
	private final Runnable closedCallback;
	
	public EditHudGui(Runnable closedCallback)
	{
		this.closedCallback = closedCallback;
	}
	
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		closedCallback.run();
	}
	
	
	//
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException
	{
		if(HudManager.keyTyped(typedChar, keyCode))
			return;
		
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		HudManager.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
	{
		HudManager.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state)
	{
		HudManager.mouseReleased(mouseX, mouseY, state);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
	{
		HudManager.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
	}
}
