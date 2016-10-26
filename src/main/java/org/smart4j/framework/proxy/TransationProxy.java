package org.smart4j.framework.proxy;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.annotation.Transaction;
import org.smart4j.framework.helper.DatabaseHelper;

public class TransationProxy implements Proxy {
	private static final Logger logger = LoggerFactory.getLogger(TransationProxy.class);
	private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>() {
		@Override
		protected Boolean initialValue() {
			return false;
		}
	};

	public Object doProxy(ProxyChain chain) throws Throwable {
		// TODO Auto-generated method stub
		Object result = null;
		boolean flag = FLAG_HOLDER.get();
		Method method = chain.getTargetMethod();
		if (!flag && method.isAnnotationPresent(Transaction.class)) {
			FLAG_HOLDER.set(true);
			try {
				DatabaseHelper.beginTransation();
				result = chain.doProxyChain();
				DatabaseHelper.commitTransation();
			} catch (Exception e) {
				DatabaseHelper.rollbackTransation();
				logger.error("rollback transaction", e);
			} finally {
				FLAG_HOLDER.remove();
			}

		} else {
			result = chain.doProxyChain();
		}
		return result;
	}

}
