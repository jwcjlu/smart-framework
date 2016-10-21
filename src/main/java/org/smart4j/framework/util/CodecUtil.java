package org.smart4j.framework.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 解码编码工具类
 * @author jinwei
 *
 */
public final class CodecUtil {
	private static final Logger logger = LoggerFactory.getLogger(CodecUtil.class);
	/**
	 * 解码
	 * @param source
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static  String decodeURL(String source){
		String target=null;
		try{
		target=URLDecoder.decode(source);
		}catch(Exception e){
			logger.error("url  decode failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}
	/**
	 * 编码
	 * @param source
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static  String encodeURL(String source){
		String target=null;
		try{
		target=URLEncoder.encode(source);
		}catch(Exception e){
			logger.error("url  decode failure",e);
			throw new RuntimeException(e);
		}
		return target;
	}

}
