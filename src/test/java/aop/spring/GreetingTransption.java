package aop.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
@Aspect
@Component
public class GreetingTransption {
	@Around("@annotation(aop.spring.Tranction)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
		before();
		Object result=pjp.proceed();
		after();
		return result;
		
	}
	public void before(){
		System.out.println("开启数据库事务");
	}
	public void after(){
		System.out.println("关闭数据库事务");
	}
}
