package aop.spring;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;
import org.springframework.stereotype.Component;
@Component
public class GreetingIntroAdvice extends DelegatingIntroductionInterceptor implements Apology{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6299384498812853400L;

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		// TODO Auto-generated method stub
		return super.invoke(mi);
	}

	public void apology(String name) {
		// TODO Auto-generated method stub
		System.out.println("sorry "+name);
		
	}

}
