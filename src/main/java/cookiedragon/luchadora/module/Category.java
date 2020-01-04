package cookiedragon.luchadora.module;

/**
 * @author cookiedragon234 15/Dec/2019
 */
public enum Category
{
	COMBAT("Combat"),
	UI("UI", false),
	PLAYER("Player"),
	RENDER("Render"),
	MOVEMENT("Movement"),
	DEV("Dev");
	
	public final String displayName;
	public final boolean visible;
	Category(String displayName)
	{
		this(displayName, true);
	}
	
	Category(String displayName, boolean visible)
	{
		this.displayName = displayName;
		this.visible =  visible;
	}
}
