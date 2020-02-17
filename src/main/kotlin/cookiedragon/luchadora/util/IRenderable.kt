package cookiedragon.luchadora.util

/**
 * @author cookiedragon234 17/Feb/2020
 */
interface IRenderable {
	fun render(mousePos: Vec2f)
	fun mouseClick(mousePos: Vec2f, mouseID: Int): Boolean
	fun mouseRelease(mousePos: Vec2f, mouseID: Int): Boolean
	fun mouseClickMove(mousePos: Vec2f, mouseID: Int): Boolean
}
