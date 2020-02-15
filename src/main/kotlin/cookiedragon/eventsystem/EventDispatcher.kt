package cookiedragon.eventsystem

/**
 * @author cookiedragon234 15/Feb/2020
 */
interface EventDispatcher {
	/**
	 * Dispatches the given event to any registered and subscribed listeners targeting
	 * this event's class or subclasses
	 *
	 * @param event The event to dispatch
	 * @return The same event♦
	 */
	fun <T: Any> dispatch(event: T): T
	
	
	/**
	 * Registers *static* methods of the given class
	 *
	 * During registration methods of the given class are indexed, but not subscribed
	 * Subscribers will not receive events until a registered class is subscribed
	 *
	 * @param subscriber An uninitialised class
	 */
	fun register(subscriber: Class<*>)
	
	/**
	 * Registers *non static* methods of the given object
	 *
	 * During registration methods of the given object are indexed, but not subscribed
	 * Subscribers will not receive events until a registered object is subscribed
	 *
	 * @param subscriber An initialised object
	 */
	fun register(subscriber: Any)
	
	
	/**
	 * Subscribes *static* methods of the given class
	 *
	 * After subscription any registered methods within the given class will receive events
	 *
	 * @param subscriber An uninitialised and registered class
	 */
	fun subscribe(subscriber: Class<*>)
	
	/**
	 * Subscribes *non static* methods of the given object
	 *
	 * After subscription any registered methods within the given object will receive events
	 *
	 * @param subscriber An initialised and registered object
	 */
	fun subscribe(subscriber: Any)
	
	
	/**
	 * Unsubscribes *static* methods of the given class
	 *
	 * After unsubscription any registered methods within the given class will *no longer* receive events
	 *
	 * @param subscriber An uninitialised and registered class
	 */
	fun unsubscribe(subscriber: Class<*>)
	
	/**
	 * Unsubscribes *non static* methods of the given object
	 *
	 * After unsubscription any registered methods within the given object will *no longer* receive events
	 *
	 * @param subscriber An initialised and registered object
	 */
	fun unsubscribe(subscriber: Any)
	
	
	companion object: EventDispatcher {
		override fun <T : Any> dispatch(event: T): T =      EventDispatcherImpl.dispatch(event)
		
		override fun register(subscriber: Class<*>) =       EventDispatcherImpl.register(subscriber)
		override fun register(subscriber: Any) =            EventDispatcherImpl.register(subscriber)
		
		override fun subscribe(subscriber: Class<*>) =      EventDispatcherImpl.subscribe(subscriber)
		override fun subscribe(subscriber: Any) =           EventDispatcherImpl.subscribe(subscriber)
		
		override fun unsubscribe(subscriber: Class<*>) =    EventDispatcherImpl.unsubscribe(subscriber)
		override fun unsubscribe(subscriber: Any) =         EventDispatcherImpl.unsubscribe(subscriber)
	}
}
