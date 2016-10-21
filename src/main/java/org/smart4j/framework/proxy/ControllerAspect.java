package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Aspect;
import org.smart4j.framework.annotation.Controller;

@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);
	private long begin;

/*	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		// TODO Auto-generated method stub
		logger.debug("=============begin==========================");
		logger.debug(String.format("class:%s", cls.getName()));
		logger.debug(String.format("method:%s", method.getName()));
		begin=System.currentTimeMillis();
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
		// TODO Auto-generated method stub
		logger.debug(String.format("time :%dms", System.currentTimeMillis()-begin));
		logger.debug("=============end==========================");
	}*/
	@Override
	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("=============begin=打开事务=========================");
		System.out.println(String.format("class:%s", cls.getName()));
		logger.info("=============begin==========================");
		logger.info(String.format("class:%s", cls.getName()));
		logger.info(String.format("method:%s", method.getName()));
		begin=System.currentTimeMillis();
	}

	@Override
	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
		// TODO Auto-generated method stub
		logger.info(String.format("time :%dms", System.currentTimeMillis()-begin));
		logger.info("=============end==========================");
		System.out.println(String.format("time :%dms", System.currentTimeMillis()-begin));
		System.out.println("=============end===关闭事务=======================");
	}
	

}
