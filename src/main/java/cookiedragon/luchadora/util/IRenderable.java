package cookiedragon.luchadora.util;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public interface IRenderable
{
	void render(Vec2f mousePos);
	boolean mouseClick(Vec2f mousePos, int mouseID);
	boolean mouseRelease(Vec2f mousePos, int mouseID);
	boolean mouseClickMove(Vec2f mousePos, int mouseID);
}
