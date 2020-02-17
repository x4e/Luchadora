package cookiedragon.luchadora.util

/**
 * Semi-Immutable helper class for dealing with two dimensional points at floating point accuracy
 *
 * @author cookiedragon234 17/Feb/2020
 */

class Vec2f {
	@JvmField
	var x: Float
	@JvmField
	var y: Float
	
	constructor(x: Float, y: Float) {
		this.x = x
		this.y = y
	}
	
	constructor(x: Float, y: Float, scale: Float) {
		this.x = x * scale
		this.y = y * scale
	}
	
	constructor(other: Vec2f) {
		x = other.x
		y = other.y
	}
	
	fun copy(): Vec2f {
		return Vec2f(this)
	}
	
	fun contains(size: Vec2f, other: Vec2f): Boolean {
		return other.x >= x && other.y >= y && other.x <= x + size.x && other.y <= y + size.y
	}
	
	fun add(b: Vec2f): Vec2f {
		return Vec2f(x + b.x, y + b.y)
	}
	
	fun subtract(b: Vec2f): Vec2f {
		return Vec2f(x - b.x, y - b.y)
	}
	
	fun scale(scale: Float): Vec2f {
		return Vec2f(
				x * scale,
				y * scale
		)
	}
	
	override fun toString(): String {
		return "($x,$y)"
	}
}
