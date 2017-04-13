package com.lys.itschoolapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {

	public static String decode(InputStream is) throws IOException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len = 0;
		while((len=is.read(buff)) > 0) {
			baos.write(buff, 0, len);
		}
		
		return baos.toString();
	}

}
