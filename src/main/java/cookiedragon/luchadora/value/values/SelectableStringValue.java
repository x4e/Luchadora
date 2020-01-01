package cookiedragon.luchadora.value.values;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cookiedragon234 06/Dec/2019
 */
public class SelectableStringValue extends StringValue
{
	public final List<String> options;
	
	public SelectableStringValue(String name, String defaultVal, String... options)
	{
		this(name, defaultVal, Stream.of(options).collect(Collectors.toCollection(ArrayList::new)));
	}
	
	public SelectableStringValue(String name, String defaultVal, List<String> options)
	{
		super(name, defaultVal);
		this.options = options;
		
		options.add(defaultVal);
	}
	
	@Override
	public void setValue(String value)
	{
		if (this.options.contains(value))
			super.setValue(value);
	}
}
