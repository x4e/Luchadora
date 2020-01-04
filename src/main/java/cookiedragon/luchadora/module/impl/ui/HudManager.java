package cookiedragon.luchadora.module.impl.ui;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.client.Render2dEvent;
import cookiedragon.luchadora.event.luchadora.ModuleInitialisationEvent;
import cookiedragon.luchadora.module.ModuleManager;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.EditHudGui;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.GuiModule;
import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.Vec2f;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * @author cookiedragon234 17/Dec/2019
 */
public class HudManager implements Globals
{
	public static ArrayList<AbstractHudElement> hudElements = new ArrayList<>();
	private static GuiModule guiModule;
	
	public static void init()
	{
		EventDispatcher.register(HudManager.class);
		EventDispatcher.subscribe(HudManager.class);
	}
	
	@Subscriber
	private static void onModuleInit(ModuleInitialisationEvent.Post event)
	{
		guiModule = ModuleManager.getModule(GuiModule.class);
	}
	
	public static AbstractHudElement getMouseOver(Vec2f mousePos)
	{
		// Loop Backwards
		for (int i = hudElements.size() - 1; i >= 0; i--)
		{
			AbstractHudElement hudElement = hudElements.get(i);
			if (hudElement.isMouseOver(mousePos))
				return hudElement;
		}
		return null;
	}
	
	public static void bringToFront(AbstractHudElement hudElement)
	{
		hudElements.remove(hudElement);
		hudElements.add(hudElement);
	}
	
	@Subscriber
	private static void onRender2d(Render2dEvent event)
	{
		if (!(mc.getCurrentScreen() instanceof EditHudGui))
		{
			Vec2f mousePos = new Vec2f(Mouse.getX(), Mouse.getY(), 1/guiModule.guiScale.getValue().floatValue());
			GlStateManager.pushAttrib();
			GlStateManager.scale(guiModule.guiScale.getValue().floatValue(), guiModule.guiScale.getValue().floatValue(), 1);
			for (AbstractHudElement hudElement : hudElements)
			{
				hudElement.render(mousePos);
			}
			GlStateManager.popAttrib();
		}
	}
	
	
	
	// --- From EditHudGui
	
	public static boolean keyTyped(char typedChar, int keyCode)
	{
		Key key = Key.fromCode(keyCode);
		for (AbstractHudElement hudElement : hudElements)
		{
			if (hudElement.keyTyped(key))
				return true;
		}
		return false;
	}
	
	public static void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		Vec2f mousePos = new Vec2f(mouseX, mouseY, 1/guiModule.guiScale.getValue().floatValue());
		GlStateManager.pushAttrib();
		GlStateManager.scale(guiModule.guiScale.getValue().floatValue(), guiModule.guiScale.getValue().floatValue(), 1);
		for (AbstractHudElement hudElement : hudElements)
		{
			hudElement.render(mousePos);
		}
		GlStateManager.popAttrib();
	}
	
	public static void mouseClicked(int mouseX, int mouseY, int mouseID)
	{
		Vec2f mousePos = new Vec2f(mouseX, mouseY, 1/guiModule.guiScale.getValue().floatValue());
		ArrayList<AbstractHudElement> copy = new ArrayList<>(hudElements);
		for (int i = copy.size() - 1; i >= 0; i--)
		{
			if (copy.get(i).mouseClick(mousePos, mouseID))
				return;
		}
	}
	
	public static void mouseReleased(int mouseX, int mouseY, int mouseID)
	{
		Vec2f mousePos = new Vec2f(mouseX, mouseY, 1/guiModule.guiScale.getValue().floatValue());
		ArrayList<AbstractHudElement> copy = new ArrayList<>(hudElements);
		for (int i = copy.size() - 1; i >= 0; i--)
		{
			if (copy.get(i).mouseRelease(mousePos, mouseID))
				return;
		}
	}
	
	public static void mouseClickMove(int mouseX, int mouseY, int mouseID, long timeSinceLastClick)
	{
		Vec2f mousePos = new Vec2f(mouseX, mouseY, 1/guiModule.guiScale.getValue().floatValue());
		ArrayList<AbstractHudElement> copy = new ArrayList<>(hudElements);
		for (int i = copy.size() - 1; i >= 0; i--)
		{
			if (copy.get(i).mouseClickMove(mousePos, mouseID))
				return;
		}
	}
}
