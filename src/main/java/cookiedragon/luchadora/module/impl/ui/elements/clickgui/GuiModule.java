package cookiedragon.luchadora.module.impl.ui.elements.clickgui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.value.values.ColourValue;
import cookiedragon.luchadora.value.values.NumberValue;

import java.awt.*;

/**
 * @author cookiedragon234 15/Dec/2019
 */
@AbstractModule.Declaration(name = "Gui", description = "Graphical User Interface", category = Category.UI, defaultBind = Key.KEY_P)
public class GuiModule extends AbstractModule
{
	public final ColourValue primaryColour = new ColourValue("Primary", new Color(0,0,0, 220));
	public final ColourValue secondaryColour = new ColourValue("Secondary", new Color(0,0,0, 210));
	public final ColourValue tertiaryColour = new ColourValue("Tertiary", new Color(0,0,0, 195));
	public final ColourValue negativeColour = new ColourValue("Negative", new Color(64, 64, 64, 175));
	public final ColourValue textColour = new ColourValue("Text", new Color(255, 255, 255));
	public final NumberValue guiScale = new NumberValue("Scale", 1f, 0.5, 2, true);
	
	@Override
	protected void onEnabled()
	{
		System.out.println("On gui Enabled");
		mc.displayGuiScreen(new EditHudGui(() -> this.enabled.setValue(false, true)));
	}
	
	@Override
	protected void onDisabled()
	{
		System.out.println("On gui Disabled");
		mc.displayGuiScreen(null);
	}
}
