package org.smart4j.framework.helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.smart4j.framework.util.ReflectionUtil;

public class BeanHelper {
	private static final Map<Class<?>,Object>BEAN_MAP=new HashMap<Class<?>,Object>();
	static {
		Set<Class<?>>classSet=ClassHelper.getBeanClassSet();
		for(Class<?> cls:classSet){
			BEAN_MAP.put(cls, ReflectionUtil.newInstance(cls));
		}
	}
	public static Map<Class<?>,Object> getBeanMap(){
		return BEAN_MAP;
	}
	public static <T>T getBean(Class<T>cls){
		if(!BEAN_MAP.containsKey(cls)){
			throw new RuntimeException("can not get bean by class "+cls);
		}
		@SuppressWarnings("unchecked")
		T t = (T) getBeanMap().get(cls);
		return t;
	}
	public static <T>void setBean(Class<T>cls,Object obj){
		BEAN_MAP.put(cls, obj);
	}

}
