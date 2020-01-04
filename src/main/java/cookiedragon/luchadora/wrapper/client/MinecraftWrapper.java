package cookiedragon.luchadora.wrapper.client;

import cookiedragon.luchadora.util.Vec2f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.math.RayTraceResult;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class MinecraftWrapper
{
	private static final Minecraft mc = Minecraft.getMinecraft();
	public final FontRendererWrapper fontRenderer = new FontRendererWrapper(mc);
	public final RenderEngineWrapper renderEngine = new RenderEngineWrapper(mc);
	public final ResourceManagerWrapper resourceManager = new ResourceManagerWrapper(mc);
	public final NetHandlerWrapper connection = new NetHandlerWrapper(mc);
	public final PlayerWrapper player = new PlayerWrapper(mc);
	public final WorldWrapper world = new WorldWrapper(mc);
	
	public final RenderGlobal getRenderGlobalActual()
	{
		return mc.renderGlobal;
	}
	
	public Vec2f getDisplaySize()
	{
		return new Vec2f(mc.displayWidth, mc.displayHeight);
	}
	
	public void displayGuiScreen(GuiScreen screen)
	{
		mc.displayGuiScreen(screen);
	}
	
	public GuiScreen getCurrentScreen()
	{
		return mc.currentScreen;
	}
	
	public GuiIngame getIngameGui()
	{
		return mc.ingameGUI;
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
	
	public RayTraceResult getMouseOver()
	{
		return mc.objectMouseOver;
	}
}
