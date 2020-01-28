package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.kotlin.ExtensionsKt;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.RenderUtils;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.BooleanValue;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.util.Arrays;

/**
 * @author cookiedragon234 22/Dec/2019
 */
@SuppressWarnings("Duplicates")
public class BooleanValueElement extends ValueElement<BooleanValue>
{
	private boolean isEnabledValue;
	
	public BooleanValueElement(BooleanValue value, ModuleElement categoryElement)
	{
		super(value, categoryElement);
		isEnabledValue = value.getName().equals("Enabled");
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
		if (moduleElement.collapsed && !isEnabledValue)
		{
			size = new Vec2f(0,0);
			return;
		}
		
		size = new Vec2f(
			100,
			mc.fontRenderer.FONT_HEIGHT + 2
		);
		
		if (!isEnabledValue)
		{
			position.x += 2;
			size.x -= 2;
		}
		
		Color onColour = isEnabledValue ?
			moduleElement.categoryElement.guiModule.secondaryColour.getValue()
			:
			moduleElement.categoryElement.guiModule.tertiaryColour.getValue();
		
		RenderUtils.renderRectangle(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			value.getValue() ? onColour.getRGB() : moduleElement.categoryElement.guiModule.negativeColour.getValue().getRGB()
		);
		
		RenderUtils.renderOutline(
			position.x,
			position.y,
			position.x + size.x,
			position.y + size.y,
			new Color(0,0,0, 50).getRGB()
		);
		
		
		ExtensionsKt.drawString(
			mc.fontRenderer,
			isEnabledValue ? this.moduleElement.module.getName() : this.value.getName(),
			position.x + 1.5f,
			position.y + 1.5f,
			moduleElement.categoryElement.guiModule.textColour.getValue().getRGB()
		);
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		if (position.contains(size, mousePos))
		{
			if (mouseID == 0)
			{
				this.value.setValue(!this.value.getValue());
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
				return true;
			}
			else if (mouseID == 1 && isEnabledValue)
			{
				this.moduleElement.collapsed = !this.moduleElement.collapsed;
				mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_TOAST_IN, 1.0f));
				return true;
			}
		}
		
		return false;
	}
}
