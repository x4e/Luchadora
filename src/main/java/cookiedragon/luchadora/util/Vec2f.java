package cookiedragon.luchadora.util;

/**
 * @author cookiedragon234 16/Dec/2019
 */
public class Vec2f
{
	public float x,y;
	
	public Vec2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vec2f(float x, float y, float scale)
	{
		this.x = x * scale;
		this.y = y * scale;
	}
	
	public Vec2f(Vec2f other)
	{
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vec2f copy()
	{
		return new Vec2f(this);
	}
	
	public boolean contains(Vec2f size, Vec2f other)
	{
		return (
			other.x >= x
			&&
			other.y >= y
			&&
			other.x <= x + size.x
			&&
			other.y <= y + size.y
		);
	}
	
	public Vec2f add(Vec2f b)
	{
		return new Vec2f(x + b.x, y + b.y);
	}
	
	public Vec2f subtract(Vec2f b)
	{
		return new Vec2f(x - b.x, y - b.y);
	}
	
	public Vec2f scale(float scale)
	{
		return new Vec2f(
			x * scale,
			y * scale
		);
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
}
