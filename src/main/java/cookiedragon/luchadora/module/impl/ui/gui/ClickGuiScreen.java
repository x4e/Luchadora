package cookiedragon.luchadora.module.impl.ui.gui;

import cookiedragon.luchadora.util.Vec2f;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public class ClickGuiScreen extends GuiScreen
{
	private final Runnable closedCallback;
	
	public ClickGuiScreen(Runnable closedCallback)
	{
		this.closedCallback = closedCallback;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		GuiManager.INSTANCE.render(new Vec2f(mouseX, mouseY));
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
	{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		GuiManager.INSTANCE.mouseClick(new Vec2f(mouseX, mouseY), mouseButton);
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int mouseButton)
	{
		super.mouseReleased(mouseX, mouseY, mouseButton);
		GuiManager.INSTANCE.mouseRelease(new Vec2f(mouseX, mouseY), mouseButton);
	}
	
	@Override
	protected void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick)
	{
		super.mouseClickMove(mouseX, mouseY, mouseButton, timeSinceLastClick);
		GuiManager.INSTANCE.mouseClickMove(new Vec2f(mouseX, mouseY), mouseButton);
	}
	
	@Override
	public void onGuiClosed()
	{
		super.onGuiClosed();
		closedCallback.run();
	}
}
