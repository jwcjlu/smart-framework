package org.smart4j.framework.proxy;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 代理的管理类
 * @author jinwei
 *
 */
public class ProxyManager {
	@SuppressWarnings("unchecked")
	public static <T>T createProxy(final Class<?>targetClass,final List<Proxy> proxyList){
		return (T)Enhancer.create(targetClass, new MethodInterceptor() {
			
			public Object intercept(Object target, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
				// TODO Auto-generated method stub
				ProxyChain  chain=new ProxyChain(targetClass, target, method, methodProxy, params, proxyList);
				return chain.doProxyChain();
			}
		});
		
	}

}
