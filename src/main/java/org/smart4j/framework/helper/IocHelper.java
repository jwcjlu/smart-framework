package org.smart4j.framework.helper;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

import org.smart4j.framework.annotation.Inject;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.ReflectionUtil;

public class IocHelper {
	static{
		Map<Class<?>,Object> beanMap=BeanHelper.getBeanMap();
		for(Entry<Class<?>, Object> beanEntry:beanMap.entrySet()){
			Class<?>beanEntryClass=beanEntry.getKey();
			Object instance=beanEntry.getValue();
			Field[]fields=beanEntryClass.getDeclaredFields();
			if(ArrayUtil.isNotEmpty(fields)){
				for(Field field:fields){
					 // 是否有inject注入标志
					if(field.isAnnotationPresent(Inject.class)){
						Class<?> beanFieldClass=field.getType();
						Object beanFieldValue=beanMap.get(beanFieldClass);
						if(beanFieldValue!=null)
						ReflectionUtil.setField(instance, field, beanFieldValue);
					}
				}
			}
		}
	}

}
