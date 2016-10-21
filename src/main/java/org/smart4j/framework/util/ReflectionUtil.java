package org.smart4j.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReflectionUtil {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 实例化一个对象
	 * @param cls
	 * @return
	 */
	public static Object newInstance(Class<?> cls) {
		Object instance = null;
		try {
			instance = cls.newInstance();
		} catch (Exception e) {
			logger.error("new a instance  failure ", e);
			throw new RuntimeException(e);
		}
		return instance;

	}

	/**
	 * 执行一个方法
	 * @param target
	 * @param method
	 * @param param
	 * @return
	 */
	public static Object invokeMethod(Object target, Method method, Object... param) {
		Object obj = null;
		try {
			method.setAccessible(true);
			obj = method.invoke(target, param);
		} catch (Exception e) {
			// TODO Auto-generated catch block

			logger.error("method invoke  failure ", e);
			throw new RuntimeException(e);
		}
		return obj;

	}

	/**
	 * 设置一个属性值
	 * @param obj
	 * @param field
	 * @param value
	 */
	public static void setField(Object obj, Field field, Object value) {

		try {
			field.setAccessible(true);
			field.set(obj, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("set field failure ", e);
			throw new RuntimeException(e);
		}

	}
}
