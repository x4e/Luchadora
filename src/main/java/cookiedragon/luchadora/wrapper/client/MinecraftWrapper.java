package cookiedragon.luchadora.wrapper.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class MinecraftWrapper
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	public final FontRendererWrapper fontRenderer = new FontRendererWrapper(mc.fontRenderer);
	
	public void displayGuiScreen(GuiScreen screen)
	{
		mc.displayGuiScreen(screen);
	}
	
	public GuiScreen getCurrentScreen()
	{
		return mc.currentScreen;
	}
	
	public PlayerWrapper getPlayer()
	{
		return new PlayerWrapper(mc.player);
	}
	
	public ServerDataWrapper getCurrentServerData()
	{
		if (mc.getCurrentServerData() == null)
			return null;
		
		return new ServerDataWrapper(mc.getCurrentServerData());
	}
	
	public boolean isSinglePlayer()
	{
		return mc.isIntegratedServerRunning();
	}
}
