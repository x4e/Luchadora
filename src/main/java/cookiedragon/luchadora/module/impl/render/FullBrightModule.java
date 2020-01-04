package cookiedragon.luchadora.module.impl.render;

import cookiedragon.luchadora.event.api.Subscriber;
import cookiedragon.luchadora.event.client.UpdateLightmapEvent;
import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.value.values.NumberValue;

/**
 * @author cookiedragon234 01/Jan/2020
 */
@AbstractModule.Declaration(name = "Full Bright", description = "Get rid of darkness", category = Category.RENDER)
public class FullBrightModule extends AbstractModule
{
	private NumberValue brightness = new NumberValue("Brightness", 1f, 0, 3);
	
	public FullBrightModule()
	{
		brightness.addCallback((oldVal, newVal) ->
			updateLightmap(newVal.floatValue()));
	}
	
	@Subscriber
	private void onUpdateLightmap(UpdateLightmapEvent event)
	{
		updateLightmap(brightness.getValue().floatValue());
	}
	
	@Override
	protected void onEnabled()
	{
		updateLightmap(brightness.getValue().floatValue());
	}
	
	@Override
	protected void onDisabled()
	{
		mc.world.provider.generateLightBrightnessTable();
	}
	
	private static void updateLightmap(float brightness)
	{
		if (mc.world.nonNull())
		{
			for (int i = 0; i < mc.world.provider.getLightBrightnessTable().length; i++)
			{
				mc.world.provider.getLightBrightnessTable()[i] = brightness;
			}
		}
	}
}
