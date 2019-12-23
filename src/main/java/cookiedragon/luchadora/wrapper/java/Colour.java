package cookiedragon.luchadora.wrapper.java;

import java.awt.*;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class Colour
{
	public static final Colour WHITE = new Colour(Color.WHITE);
	public static final Colour BLACK = new Colour(Color.BLACK);
	public static final Colour ORANGE = new Colour(Color.ORANGE);
	
	public final Color color;
	
	public Colour(int r, int g, int b, int a)
	{
		this(new Color(r, g, b, a));
	}
	public Colour(Color color)
	{
		this.color = color;
	}
	
	public int getRgb()
	{
		return this.color.getRGB();
	}
}
