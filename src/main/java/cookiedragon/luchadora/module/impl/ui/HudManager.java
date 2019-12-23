package cookiedragon.luchadora.module.impl.ui;

import cookiedragon.luchadora.event.api.EventDispatcher;
import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.client.Render2dEvent;
import cookiedragon.luchadora.util.Globals;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.Vec2f;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author cookiedragon234 17/Dec/2019
 */
public class HudManager implements Globals
{
	public static ArrayList<AbstractHudElement> hudElements = new ArrayList<>();
	
	public static void init()
	{
		EventDispatcher.register(HudManager.class);
		EventDispatcher.subscribe(HudManager.class);
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
			for (AbstractHudElement hudElement : hudElements)
			{
				hudElement.render(new Vec2f(Mouse.getX(), Mouse.getY()));
			}
		}
	}
	
	
	
	// --- From EditHudGui
	
	public static void keyTyped(char typedChar, int keyCode)
	{
		Key key = Key.fromCode(keyCode);
		for (AbstractHudElement hudElement : hudElements)
		{
			hudElement.keyTyped(key);
		}
	}
	
	public static void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		for (AbstractHudElement hudElement : hudElements)
		{
			hudElement.render(new Vec2f(mouseX, mouseY));
		}
	}
	
	public static void mouseClicked(int mouseX, int mouseY, int mouseID)
	{
		ArrayList<AbstractHudElement> copy = new ArrayList<>(hudElements);
		for (int i = copy.size() - 1; i >= 0; i--)
		{
			if (copy.get(i).mouseClick(new Vec2f(mouseX, mouseY), mouseID))
				return;
		}
	}
	
	public static void mouseReleased(int mouseX, int mouseY, int mouseID)
	{
		ArrayList<AbstractHudElement> copy = new ArrayList<>(hudElements);
		for (int i = copy.size() - 1; i >= 0; i--)
		{
			if (copy.get(i).mouseRelease(new Vec2f(mouseX, mouseY), mouseID))
				return;
		}
	}
	
	public static void mouseClickMove(int mouseX, int mouseY, int mouseID, long timeSinceLastClick)
	{
		ArrayList<AbstractHudElement> copy = new ArrayList<>(hudElements);
		for (int i = copy.size() - 1; i >= 0; i--)
		{
			if (copy.get(i).mouseClickMove(new Vec2f(mouseX, mouseY), mouseID))
				return;
		}
	}
}
