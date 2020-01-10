package cookiedragon.luchadora.module.impl.combat

import cookiedragon.luchadora.module.AbstractModule
import cookiedragon.luchadora.module.Category
import cookiedragon.luchadora.util.Key
import cookiedragon.luchadora.value.values.*

/**
 * @author cookiedragon234 10/Jan/2020
 */
@AbstractModule.Declaration(name = "Crystal Aura", description = "Automatically place and break crystals", category = Category.COMBAT)
class CrystalAuraModule : AbstractModule() {
	private val booleanValue = BooleanValue("Test Boolean", true)
	private val secondKeyValue = KeyValue("Second key", Key.KEY_N)
	private val intVale = NumberValue("Int val", 2, 0, 4)
	private val floatVal = NumberValue("Float val", 0f, -2, 2.2)
	private val neg = NumberValue("neg", -3, -4, -2)
	private val selectableStringValue = SelectableStringValue("strings", "a", "a", "b")
	private val stringValue = StringValue("String Val", "hi")
	private val testEnumValue = EnumValue("Enuum", Test.TEST_A)
	
	internal enum class Test {
		TEST_A,
		TEST_B
	}
}
