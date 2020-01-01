package cookiedragon.luchadora.event.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Utility class for storing information about methods that are subscribed to events
 *
 * @author cookiedragon234 07/Dec/2019
 */
class SubscribingMethod
{
	public SubscribingMethod(Class<?> clazz, Object instance, Method method)
	{
		method.setAccessible(true);
		
		this.clazz = clazz;
		this.instance = instance;
		this.method = method;
	}
	
	final Class<?> clazz;
	final Object instance;
	final Method method;
	boolean active = false;
	
	public void invoke(Object arg) throws InvocationTargetException, IllegalAccessException
	{
		method.invoke(this.instance, arg);
	}
	
	@Override
	public String toString()
	{
		return "(" + clazz + ", " + instance + ", " + method + ", " + active + ")";
	}
}
