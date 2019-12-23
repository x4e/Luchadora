package cookiedragon.luchadora.event.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used in front of methods that subscribe to receive specific events
 * @author cookiedragon234 07/Dec/2019
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Subscriber
{
}
