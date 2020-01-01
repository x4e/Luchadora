package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.StringValue;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.regex.Pattern;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class StringValueElement extends ValueElement<StringValue>
{
	private boolean typing = false;
	
	public StringValueElement(StringValue value, ModuleElement categoryElement)
	{
		super(value, categoryElement);
	}
	
	@SuppressWarnings("Duplicates")
	@Override
	public void render(Vec2f mousePos)
	{
		if (moduleElement.collapsed)
		{
			size = new Vec2f(0,0);
			return;
		}
		
		size = new Vec2f(
			100,
			mc.fontRenderer.getFontHeight() + 2
		);
		
		position.x += 2;
		size.x -= 2;
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			typing ? Color.ORANGE.getRGB() : Color.DARK_GRAY.getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			Color.BLACK.getRGB()
		);
		
		mc.fontRenderer.drawStringClamped(
			this.value.getName(),
			position.x + 1,
			position.y + 1,
			size.x / 2,
			Color.LIGHT_GRAY.getRGB()
		);
		
		mc.fontRenderer.drawStringRightClamped(
			this.value.getValue(),
			position.x + size.x,
			position.y + 1,
			size.x / 2,
			Color.LIGHT_GRAY.getRGB()
		);
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if(position.contains(size, mousePos) && mouseID == 0)
		{
			this.typing = true;
			return true;
		}
		
		this.typing = false;
		return false;
	}
	
	@Override
	public boolean mouseRelease(Vec2f mousePos, int mouseID)
	{
		if(position.contains(size, mousePos) && mouseID == 0)
		{
			this.typing = true;
			return true;
		}
		
		this.typing = false;
		return false;
	}
	
	
	@Override
	public boolean keyTyped(Key key)
	{
		if (!this.typing)
			return false;
		
		if (key == Key.KEY_ESCAPE)
		{
			this.typing = false;
			return true;
		}
		
		if(key == Key.KEY_BACK && !Keyboard.isRepeatEvent() && this.value.getValue().length() > 0)
		{
			this.value.setValue(this.value.getValue().substring(0, value.getValue().length() - 1));
			return false;
		}
		
		String keyStr = Character.toString(Keyboard.getEventCharacter());
		
		if(keyStr.length() <= 0)
			return false;
		
		if((Key.KEY_LSHIFT.isKeyDown() || Key.KEY_RSHIFT.isKeyDown()) ^ Key.KEY_CAPITAL.isKeyDown())
		{
			keyStr = keyStr.toUpperCase();
		}
		else
		{
			keyStr = keyStr.toLowerCase();
		}
		
		if(this.typing && ChatAllowedCharacters.isAllowedCharacter(keyStr.charAt(0)) && !keyStr.contains("\n"))
		{
			this.value.setValue(this.value.getValue() + keyStr);
			return true;
		}
		return false;
	}
}
