package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.valuesystem.Value;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;

import java.awt.*;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class StringValueElement extends ValueElement<Value<String>>
{
	private boolean typing = false;
	
	public StringValueElement(Value<String> value, ModuleElement categoryElement)
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
			mc.fontRenderer.FONT_HEIGHT + 2
		);
		
		position.x += 2;
		size.x -= 2;
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			typing ? Color.ORANGE.getRGB() : moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			new Color(0,0,0, 50).getRGB()
		);
		
		ExtensionsKt.drawStringClamped(
			mc.fontRenderer,
			this.value.getName(),
			position.x + 1,
			position.y + 1,
			size.x / 2,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
		
		ExtensionsKt.drawStringRightClamped(
			mc.fontRenderer,
			this.value.getValue(),
			position.x + size.x,
			position.y + 1,
			size.x / 2,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (position.contains(size, mousePos) && mouseID == 0)
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
		if (position.contains(size, mousePos) && mouseID == 0)
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
		
		if (key == Key.KEY_BACK && !Keyboard.isRepeatEvent() && this.value.getValue().length() > 0)
		{
			this.value.setValue(this.value.getValue().substring(0, value.getValue().length() - 1));
			return false;
		}
		
		String keyStr = Character.toString(Keyboard.getEventCharacter());
		
		if (keyStr.length() <= 0)
			return false;
		
		if ((Key.KEY_LSHIFT.isKeyDown() || Key.KEY_RSHIFT.isKeyDown()) ^ Key.KEY_CAPITAL.isKeyDown())
		{
			keyStr = keyStr.toUpperCase();
		}
		else
		{
			keyStr = keyStr.toLowerCase();
		}
		
		if (this.typing && ChatAllowedCharacters.isAllowedCharacter(keyStr.charAt(0)) && !keyStr.contains("\n"))
		{
			this.value.setValue(this.value.getValue() + keyStr);
			return true;
		}
		return false;
	}
}
