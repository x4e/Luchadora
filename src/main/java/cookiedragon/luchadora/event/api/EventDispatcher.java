package cookiedragon.luchadora.event.api;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>An annotated event bus implementation</p>
 *
 * <p>It is designed with the basis that classes are registered once, indexing their methods for potential event subscribers.</p>
 *
 * <p>This means the class can be frequently subscribed and unsubscribed with minimal performance impact</p>
 *
 * <p>A limitation of this event bus implementation is that subscribed methods cannot be inherited from super classes.
 * I might add this at a later date</p>
 *
 * @author cookiedragon234 07/Dec/2019
 */
public class EventDispatcher
{
	private static final Map<Class<?>, Set<SubscribingMethod>> subscriptions = new ConcurrentHashMap<>();
	
	/**
	 * <p>This will index the given class, searching it for methods that subscribe to events. This will **NOT**
	 * subscribe the class to receive events. In order to that you need to, well, subscribe it! Just use the
	 * subscribe function and pass the same class.</p>
	 *
	 * <p><b>This will only index non-static methods</b></p>
	 *
	 * @param subscriber An initialised class containing methods to be subscribed
	 */
	public static void register(Object subscriber)
	{
		register(subscriber, subscriber.getClass());
	}
	
	/**
	 * <p>This will index the given class, searching it for methods that subscribe to events. This will **NOT**
	 * subscribe the class to receive events. In order to that you need to, well, subscribe it! Just use the
	 * subscribe function and pass the same class.</p>
	 *
	 * <p><b>This will only index static methods</b></p>
	 *
	 * @param clazz The class to index
	 */
	public static void register(Class clazz)
	{
		// We provide null as the subscriber instance because it is indexing static methods and therefore needs no instance to initialise
		register(null, clazz);
	}
	
	private static void register(Object subscriber, Class clazz)
	{
		for (Method declaredMethod : clazz.getDeclaredMethods())
		{
			// If we are registering a static class then only allow static methods to be indexed!
			if (subscriber == null && !Modifier.isStatic(declaredMethod.getModifiers()))
				continue;
			
			// On the other hand, if we are indexing an initialised class then skip static methods!
			if (subscriber != null && Modifier.isStatic(declaredMethod.getModifiers()))
				continue;
			
			if (!declaredMethod.isAnnotationPresent(Subscriber.class))
				continue;
			
			if (declaredMethod.getParameterCount() != 1)
				throw new RuntimeException("Subscribing event must have only 1 parameter!");
			
			Class<?> eventType = declaredMethod.getParameterTypes()[0];
			
			if (!AbstractEvent.class.isAssignableFrom(eventType))
				throw new RuntimeException(String.format("Registered method '%s' parameter needs to extends AbstractEvent", declaredMethod.toString()));
			
			if (!subscriptions.containsKey(eventType))
				subscriptions.put(eventType, new HashSet<>());
			
			subscriptions.get(eventType).add(new SubscribingMethod(clazz, subscriber, declaredMethod));
		}
	}
	
	/**
	 * Subscribes the given class, meaning that it's methods will be eligible to recieve events.
	 * You **MUST** register the class before subscribing it.
	 *
	 * @param subscriber The class to subscribe
	 */
	public static void subscribe(Class subscriber)
	{
		for (Set<SubscribingMethod> subscribingMethods : subscriptions.values())
		{
			for (SubscribingMethod subscribingMethod : subscribingMethods)
			{
				if (subscribingMethod.clazz == subscriber)
				{
					subscribingMethod.active = true;
				}
			}
		}
	}
	
	/**
	 * Subscribes the given class, meaning that it's methods will be eligible to recieve events.
	 * You **MUST** register the class before subscribing it.
	 *
	 * @param subscriber The class to subscribe
	 */
	public static void subscribe(Object subscriber)
	{
		for (Set<SubscribingMethod> subscribingMethods : subscriptions.values())
		{
			for (SubscribingMethod subscribingMethod : subscribingMethods)
			{
				if (subscribingMethod.instance == subscriber)
				{
					subscribingMethod.active = true;
				}
			}
		}
	}
	
	/**
	 * Unsubscribe the class, therefore making it ineligible to receive events.
	 *
	 * @param subscriber The class to subscribe
	 */
	public static void unsubscribe(Class subscriber)
	{
		for (Set<SubscribingMethod> subscribingMethods : subscriptions.values())
		{
			for (SubscribingMethod subscribingMethod : subscribingMethods)
			{
				if (subscribingMethod.clazz == subscriber)
				{
					subscribingMethod.active = false;
				}
			}
		}
	}
	
	/**
	 * Unsubscribe the class, therefore making it ineligible to receive events.
	 *
	 * @param subscriber The class to subscribe
	 */
	public static void unsubscribe(Object subscriber)
	{
		for (Set<SubscribingMethod> subscribingMethods : subscriptions.values())
		{
			for (SubscribingMethod subscribingMethod : subscribingMethods)
			{
				if (subscribingMethod.instance == subscriber)
				{
					subscribingMethod.active = false;
				}
			}
		}
	}
	
	/**
	 * Dispatches the given event to all the methods that subscribe to it
	 *
	 * @param event The event to dispatch
	 * @return The event that was dispatched
	 */
	public static AbstractEvent dispatch(AbstractEvent event)
	{
		for (Map.Entry<Class<?>, Set<SubscribingMethod>> entry : subscriptions.entrySet())
		{
			if (entry.getKey().isAssignableFrom(event.getClass()))
			{
				Iterator<SubscribingMethod> iterator = entry.getValue().iterator();
				while (iterator.hasNext() && !event.isCancelled())
				{
					SubscribingMethod subscribingMethod = iterator.next();
					if (subscribingMethod.active)
					{
						try
						{
							subscribingMethod.invoke(event);
						}
						catch(Exception e)
						{
							throw new RuntimeException("Error while invoking event '" + event.getClass().getName() + "'", e);
						}
					}
				}
			}
		}
		
		return event;
	}
}
