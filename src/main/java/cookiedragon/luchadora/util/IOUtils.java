package cookiedragon.luchadora.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author cookiedragon234 27/Dec/2019
 */
public class IOUtils
{
	public static byte[] getBytesFromInputStream(InputStream is) throws IOException
	{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] buffer = new byte[0xFFFF];
		for (int len = is.read(buffer); len != -1; len = is.read(buffer))
		{
			os.write(buffer, 0, len);
		}
		return os.toByteArray();
	}
}
