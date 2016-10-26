package org.smart4j.framework.helper;

import org.smart4j.framework.util.ClassUtil;

public class HelperLoader {
	public static void init(){
		Class<?>[]classSets={
				ClassHelper.class,
				BeanHelper.class,
				IocHelper.class,
				AopHelper.class,
				ControllerHelper.class
		};
		for(Class<?> cls:classSets){
			ClassUtil.loadClass(cls.getName());
		}
	}

}
