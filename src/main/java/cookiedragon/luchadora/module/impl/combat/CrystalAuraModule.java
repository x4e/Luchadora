package cookiedragon.luchadora.module.impl.combat;

import cookiedragon.luchadora.module.AbstractModule;
import cookiedragon.luchadora.module.Category;
import cookiedragon.luchadora.util.Key;
import cookiedragon.luchadora.value.Value;
import cookiedragon.luchadora.value.values.*;

/**
 * @author cookiedragon234 15/Dec/2019
 */
@AbstractModule.Deceleration(name = "Crystal Aura", description = "Automatically place and break crystals", category = Category.COMBAT)
public class CrystalAuraModule extends AbstractModule
{
	private final Value<Boolean> booleanValue = new BooleanValue("Test Boolean", true);
	private final Value<Key> secondKeyValue = new KeyValue("Second key", Key.KEY_N);
	private final NumberValue<Integer> intVale = new NumberValue<Integer>("Int val", 2);
	private final SelectableStringValue selectableStringValue = new SelectableStringValue("strings", "a", "a", "b");
	private final StringValue stringValue = new StringValue("String Val", "hi");
	
	enum Test
	{
		TEST_A,
		TEST_B
	}
	
	private final EnumValue<Test> testEnumValue = new EnumValue<>("Enuum", Test.TEST_A);
	
	@Override
	protected void onEnabled()
	{
		System.out.println("On CAura Enabled");
	}
	
	@Override
	protected void onDisabled()
	{
		System.out.println("On CAura Disabled");
	}
}
