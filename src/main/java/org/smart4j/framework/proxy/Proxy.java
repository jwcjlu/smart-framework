package org.smart4j.framework.proxy;
/**
 * 代理的接口
 * @author jinwei
 *
 */
public interface Proxy {
	/**
	 * 执行连式代理
	 * @param chain
	 * @return
	 */
	Object doProxy(ProxyChain chain)throws Throwable;
}
