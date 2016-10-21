package org.smart4j.framework.helper;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import org.smart4j.framework.annotation.Controller;
import org.smart4j.framework.annotation.Service;
import org.smart4j.framework.util.ClassUtil;



public class ClassHelper {
	/**
	 * 定义的集合
	 */
	private final static Set<Class<?>>CLASS_SET;
	static{
		String basePackage=ConfigHelper.getAppBasePackage();
		CLASS_SET=ClassUtil.getClassSet(basePackage);
	}
	public static Set<Class<?>> getClassSet(){
		return CLASS_SET;
	}
	/**
	 * 得到注解service的所有class
	 * @return
	 */
	public static Set<Class<?>>  getServiceClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		if(CLASS_SET!=null){
			for(Class<?> clazz:CLASS_SET){
				if(clazz.isAnnotationPresent(Service.class)){
					classSet.add(clazz);
				}	
			}
		}
		return classSet;
		
	}
	/**
	 * 得到注解controller的所有class
	 * @return
	 */
	public static Set<Class<?>>  getControllerClassSet(){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		if(CLASS_SET!=null){
			for(Class<?> clazz:CLASS_SET){
				if(clazz.isAnnotationPresent(Controller.class)){
					classSet.add(clazz);
				}	
			}
		}
		return classSet;
	}
	/**
	 * 得到注解controller和service的所有class
	 * @return
	 */
	public static Set<Class<?>>getBeanClassSet(){
		Set<Class<?>> beanClassSet=new HashSet<Class<?>>();
		beanClassSet.addAll(getServiceClassSet());
		beanClassSet.addAll(getControllerClassSet());
		return beanClassSet;
	}
	/**
	 * 获得某个类的所有子类
	 * @param supperClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetBySuper(Class<?>supperClass){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> cls:CLASS_SET){
			if(cls.isAssignableFrom(supperClass)&&!cls.equals(supperClass)){
				classSet.add(cls);
			}
		}
		return classSet;
	}
	/**
	 * 获得某个注解的类
	 * @param annotationClass
	 * @return
	 */
	public static Set<Class<?>> getClassSetByAnnotation(Class<? extends Annotation>annotationClass){
		Set<Class<?>> classSet=new HashSet<Class<?>>();
		for(Class<?> cls:CLASS_SET){
			if(cls.isAnnotationPresent(annotationClass)){
				classSet.add(cls);
			}
		}
		return classSet;
	}

}
