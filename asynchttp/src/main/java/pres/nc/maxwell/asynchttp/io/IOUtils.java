package pres.nc.maxwell.asynchttp.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * IO工具类
 */
public class IOUtils {
	
	/**
	 * InputStream转byte[]
	 * 
	 * @param is
	 *            输入流
	 * @return byte数组
	 */
	public static byte[] inputstream2bytes(InputStream is) throws IOException {
		ByteArrayOutputStream baoStream = new ByteArrayOutputStream();

		byte[] buff = new byte[1024];
		int rc;

		while ((rc = is.read(buff, 0, 1024)) > 0) {
			baoStream.write(buff, 0, rc);
		}

		return baoStream.toByteArray();
	}
	
}
