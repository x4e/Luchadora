package cookiedragon.luchadora.module.impl.ui.elements.clickgui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.Declaration;
import cookiedragon.luchadora.util.Key;
import cookiedragon.valuesystem.NumberValue;
import cookiedragon.valuesystem.Value;

import java.awt.*;

/**
 * @author cookiedragon234 15/Dec/2019
 */
@Declaration(defaultBind = Key.KEY_P)
public class GuiModule extends AbstractModule
{
	public GuiModule()
	{
		super("Gui", "Graphical User Interface", Category.UI);
	}
	
	public final Value<Color> primaryColour = new Value<Color>("Primary", new Color(0,0,0, 220));
	public final Value<Color> secondaryColour = new Value<Color>("Secondary", new Color(0,0,0, 210));
	public final Value<Color> tertiaryColour = new Value<Color>("Tertiary", new Color(0,0,0, 195));
	public final Value<Color> negativeColour = new Value<Color>("Negative", new Color(64, 64, 64, 175));
	public final Value<Color> textColour = new Value<Color>("Text", new Color(255, 255, 255));
	public final NumberValue<Float> guiScale = new NumberValue<>("Scale", 1f, 0.5f, 2f, true);
	
	@Override
	protected void onEnabled()
	{
		System.out.println("On gui Enabled");
		getMc().displayGuiScreen(new EditHudGui(() -> this.getEnabled().setValue(false, true)));
	}
	
	@Override
	protected void onDisabled()
	{
		System.out.println("On gui Disabled");
		getMc().displayGuiScreen(null);
	}
}
