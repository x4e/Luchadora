package cookiedragon.luchadora.module.impl.ui.gui;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.module.impl.ui.EditHudGui;
import cookiedragon.luchadora.util.Key;

/**
 * @author cookiedragon234 15/Dec/2019
 */
@AbstractModule.Deceleration(name = "Gui", description = "Graphical User Interface", category = Category.UI, defaultBind = Key.KEY_P)
public class GuiModule extends AbstractModule
{
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
