package cookiedragon.luchadora.module.impl.ui.elements;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.impl.ui.AbstractHudElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.EditHudGui;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.Vec2f;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

/**
 * @author cookiedragon234 29/Dec/2019
 */
public class SearchBarElement extends AbstractHudElement
{
	public SearchBarElement()
	{
		super("Search Bar", "Filter Hacks", Category.UI);
	}
	
	private GuiTextField textField = new GuiTextField(0, mc.fontRenderer, 0, 0, 100, 10);
	
	@Override
	public void render(Vec2f mousePos)
	{
		if (this.isEnabled() &&  mc.currentScreen instanceof EditHudGui)
		{
			this.position = new Vec2f(mc.displayWidth, mc.displayHeight);
			position.x = (position.x / 2f) - (textField.width / 2f);
			position.y = 10;
			textField.drawTextBox();
		}
	}
	
	@Override
	public boolean keyTyped(Key key)
	{
		if (key != Key.KEY_NONE)
		{
			return textField.textboxKeyTyped(Keyboard.getEventCharacter(), key.code);
		}
		return false;
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		return textField.mouseClicked((int) mousePos.x, (int) mousePos.y, mouseID);
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		return false;
	}
	
	@Override
	public boolean mouseClickMove(Vec2f mousePos, int mouseID)
	{
		return false;
	}
}
