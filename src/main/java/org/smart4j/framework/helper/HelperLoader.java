package org.smart4j.framework.helper;

import org.smart4j.framework.util.ClassUtil;

public class HelperLoader {
	public static void init(){
		Class<?>[]classSets={
				BeanHelper.class,
				ClassHelper.class,
				AopHelper.class,
				IocHelper.class,
				ControllerHelper.class
		};
		for(Class<?> cls:classSets){
			ClassUtil.loadClass(cls.getName());
		}
	}

}
