package aop.spring;

import org.springframework.stereotype.Component;

@Component
public class GreetingImpl implements Greeting{
	@Tag
	@Tranction
	public void sayHello(String name){
		System.out.println("welcome  come in "+name);
	}
	public void goodMorning(String name){
		System.out.println("good morning "+name);
	}
	public void goodNight(String name){
		System.out.println("good night  " +name);
	}
}
