package cookiedragon.luchadora.module;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public enum Category
{
	COMBAT("Combat", true),
	UI("UI", false);
	
	public final String displayName;
	public final boolean visible;
	Category(String displayName, boolean visible)
	{
		this.displayName = displayName;
		this.visible =  visible;
	}
}
