package cookiedragon.luchadora.event.api;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Objects;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author cookiedragon234 07/Dec/2019
 */
public abstract class AbstractEvent
{
	private boolean isCancelled;
	private final boolean isCancellable;
	
	public AbstractEvent()
	{
		this.isCancelled = false;
		// Cache value to prevent loading annotations continuously at runtime
		this.isCancellable = hasCancellableAnnotation();
	}
	
	/**
	 * @return true if this event has been cancelled. This means that it will not be received by future subscribers
	 * and may also affect the logic that posted the event
	 */
	public boolean isCancelled()
	{
		return this.isCancelled;
	}
	
	/**
	 * @param isCancelled whether this event is cancelled. This means that it will not be received by future subscribers
	 * and may also affect the logic that posted the event
	 */
	public void setCancelled(boolean isCancelled)
	{
		if (!isCancellable)
			throw new RuntimeException("Tried to cancel a non cancellable event!");
		
		this.isCancelled = isCancelled;
	}
	
	
	/**
	 * @return true if this class has an {@code @Cancellable} annotation, indicating it can be cancelled
	 */
	protected final boolean hasCancellableAnnotation()
	{
		try
		{
			Objects.requireNonNull(this.getClass().getAnnotation(Cancellable.class));
			return true;
		}
		catch(Exception ignored){}
		
		return false;
	}
	
	@Retention(value = RUNTIME)
	@Target(value = TYPE)
	public @interface Cancellable{}
}
