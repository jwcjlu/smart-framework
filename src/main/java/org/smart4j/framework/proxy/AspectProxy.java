package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 抽象类  模板方法
 * @author jinwei
 *
 */

public abstract class AspectProxy implements Proxy {
	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);
 
	public final Object doProxy(ProxyChain chain) throws Throwable {
		// TODO Auto-generated method stub
		Object result=null;

		
		Class<?> targetClass=chain.getTargetClass();
		Object[]params=chain.getMethodParams();
		Method targetMethod=chain.getTargetMethod();
		begin();
		try{
		if(intercept(targetClass, targetMethod, params)){
			before(targetClass, targetMethod, params);
			result=chain.doProxyChain();
			after(targetClass, targetMethod, params,result);
		}else{
			doProxy(chain);
		}
		}catch(Exception e){
			error(targetClass, targetMethod,params,e);
			logger.error("invoke failure",e);
		}finally{
			end();
		}
		return result;
	}

	public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
		return true;
	}

	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
	}

	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {

	}

	public void error(Class<?> cls, Method method, Object[] params, Throwable e) {

	}

	public void begin() {

	}

	public void end() {

	}

}
