package cookiedragon.eventsystem

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap

/**
 * @author cookiedragon234 15/Feb/2020
 */
internal object EventDispatcherImpl: EventDispatcher {
	private val subscriptions: MutableMap<Class<*>, MutableSet<SubscribingMethod>> = ConcurrentHashMap()
	
	override fun <T : Any> dispatch(event: T): T {
		var clazz: Class<*> = event.javaClass
		while (true) {
			val methods = subscriptions[clazz] ?: break
			for (method in methods) {
				if (method.active) {
					method.invoke(event)
				}
			}
			clazz = clazz.superclass
			if (clazz == Any::class.java)
				break
		}
		return event
	}
	
	override fun register(subscriber: Class<*>) = register(subscriber, null)
	
	override fun register(subscriber: Any) = register(subscriber.javaClass, subscriber)
	
	private fun register(clazz: Class<*>, instance: Any?) {
		for (method in clazz.declaredMethods) {
			// If we are registering a static class then only allow static methods to be indexed
			if (instance == null && !method.isStatic())
				continue
			
			// If we are registering an initialised class then skip static methods
			if (instance != null && method.isStatic())
				continue
			
			// Needs Subscriber annotation
			if (!method.isAnnotationPresent(Subscriber::class.java))
				continue
			
			if (method.parameterCount != 1) {
				IllegalArgumentException("Expected only 1 parameter for $clazz.${method.name}")
						.printStackTrace()
				continue
			}
			
			val eventType = method.parameterTypes[0]!!
			
			subscriptions.getOrPut(
				eventType, {
					hashSetOf()
				}
			).add(SubscribingMethod(clazz, instance, method))
		}
	}
	
	
	override fun subscribe(subscriber: Class<*>) = setActive(subscriber, true)
	
	override fun subscribe(subscriber: Any)  = setActive(subscriber, true)
	
	override fun unsubscribe(subscriber: Class<*>)  = setActive(subscriber, false)
	
	override fun unsubscribe(subscriber: Any)  = setActive(subscriber, false)
	
	private fun setActive(instance: Any?, active: Boolean) {
		for (methods in subscriptions.values) {
			for (method in methods) {
				if (method.instance == instance) {
					method.active = active
				}
			}
		}
	}
	
	private fun setActive(subscriber: Class<*>, active: Boolean) {
		for (methods in subscriptions.values) {
			for (method in methods) {
				if (method.clazz == subscriber) {
					method.active = active
				}
			}
		}
	}
}

data class SubscribingMethod(val clazz: Class<*>, val instance: Any?, val method: Method, var active: Boolean = false) {
	init {
		method.isAccessible = true
	}
	
	@Throws(Throwable::class)
	fun invoke(event: Any) {
		try {
			method.invoke(this.instance, event)
		} catch (throwable: Throwable) {
			// If the method threw an exception
			if (throwable is InvocationTargetException) {
				throw throwable.cause ?: throwable
			}
			// Otherwise ignore the exception
		}
	}
}

private fun Method.isStatic() = Modifier.isStatic(this.modifiers)
