package org.smart4j.framework.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jinwei on 2016/10/14.
 */
public final class PropsUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropsUtil.class);

	/**
	 * 加载配置文件
	 */
	public static Properties loadProps(String fileName) {
		Properties props = null;
		InputStream is = null;
		try {
			is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (is == null) {
				throw new FileNotFoundException(fileName + "file is not found");
			}
			props = new Properties();
			props.load(is);
		} catch (IOException e) {
			logger.error("load properties file failure ", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("close input stream failure", e);
				}
			}
		}
		return props;
	}

	/**
	 * 获取结果为字符串
	 * @param prop
	 * @param key
	 * @param defalutValue
	 * @return
	 */
	public static String getString(Properties prop, String key, String defalutValue) {
		String value = defalutValue;
		if (prop.containsKey(key)) {
			value = prop.getProperty(key);
		}
		return value;
	}

	/**
	 * 获取结果为字符串
	 * @param prop
	 * @param key
	 * @return
	 */

	public static String getString(Properties prop, String key) {
		return getString(prop, key, "");
	}

	/**
	 * 获取结果为int
	 * @param prop
	 * @param key
	 * @return
	 */
	public static int getInt(Properties prop, String key) {
		return getInt(prop, key, 0);
	}

	/**
	 * 获取结果为int
	 * @param prop
	 * @param key
	 * @param defalutValue
	 * @return
	 */
	public static int getInt(Properties prop, String key, int defalutValue) {
		int value = defalutValue;
		if (prop.containsKey(key)) {
			value = CastUtil.castInt(prop.getProperty(key));
		}
		return value;
	}

	/**
	 * 获取结果为boolean
	 * @param prop
	 * @param key
	 * @return
	 */

	public static boolean getBoolean(Properties prop, String key) {
		return getBoolean(prop, key, false);
	}

	/**
	 * 获取结果为boolean
	 * @param prop
	 * @param key
	 * @param defalutValue
	 * @return
	 */

	public static boolean getBoolean(Properties prop, String key, boolean defalutValue) {
		boolean value = defalutValue;
		if (prop.containsKey(key)) {
			value = CastUtil.castBoolean(prop.getProperty(key));
		}
		return value;
	}

}
