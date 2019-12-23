package cookiedragon.luchadora.module.impl.ui.elements.clickgui.values;

import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ModuleElement;
import cookiedragon.luchadora.module.impl.ui.elements.clickgui.ValueElement;
import cookiedragon.luchadora.util.Vec2f;
import cookiedragon.luchadora.value.values.SelectableStringValue;

/**
 * @author cookiedragon234 22/Dec/2019
 */
public class SelectableStringValueElement extends ValueElement<SelectableStringValue>
{
	public SelectableStringValueElement(SelectableStringValue value, ModuleElement categoryElement)
	{
		super(value, categoryElement);
	}
	
	@Override
	public void render(Vec2f mousePos)
	{
	
	}
	
	@Override
	public boolean mouseClick(Vec2f mousePos, int mouseID)
	{
		return false;
	}
}
