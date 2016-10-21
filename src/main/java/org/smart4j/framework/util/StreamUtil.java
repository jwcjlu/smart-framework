package org.smart4j.framework.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class StreamUtil {
	private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);
	public static String getString(InputStream input){
		StringBuilder result=new StringBuilder();
		try{
		BufferedReader  reader=new BufferedReader(new  InputStreamReader(input));
		String line=null;
		while((line=reader.readLine())!=null){
			result.append(line);
		}
		}catch(Exception e){
			logger.error("change to stream failure",e);
			throw new RuntimeException(e);
		}
		
		return result.toString();
		
	}

}
