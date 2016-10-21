package aop.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class GreetingAspect {
	@DeclareParents(value="aop.spring.GreetingImpl",defaultImpl=ApologyImpl.class)
	private Apology apology;
	@Around("@annotation(aop.spring.Tag)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
	
		
		GreetingImpl obj=(GreetingImpl) pjp.getTarget();
		 Object[] objs=pjp.getArgs();
		 obj.goodMorning(objs[0].toString());
		return null;
		
	}
	public void before(){
		System.out.println("Before");
	}
	public void after(){
		System.out.println("After");
	}

}
