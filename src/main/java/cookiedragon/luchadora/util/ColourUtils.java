package cookiedragon.luchadora.util;

import java.awt.*;
import java.util.HashMap;

/**
 * @author cookiedragon234 05/Jan/2020
 */
public class ColourUtils
{
	/**
	 *
	 * @param hue 0 - 1
	 * @param saturation 0 - 1
	 * @param value 0 - 1
	 * @param alpha 0 - 1
	 * @return
	 */
	public static Color hsvToRgb(float hue, float saturation, float value, float alpha) {
		
		int h = (int)(hue * 6);
		float f = hue * 6 - h;
		float p = value * (1 - saturation);
		float q = value * (1 - f * saturation);
		float t = value * (1 - (1 - f) * saturation);
		
		switch (h) {
			case 6:
			case 0: return new Color(value, t, p, alpha);
			case 1: return new Color(q, value, p, alpha);
			case 2: return new Color(p, value, t, alpha);
			case 3: return new Color(p, q, value, alpha);
			case 4: return new Color(t, p, value, alpha);
			case 5: return new Color(value, p, q, alpha);
			default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
		}
	}
}
