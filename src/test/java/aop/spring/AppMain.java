package aop.spring;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.helper.HelperLoader;
import org.smart4j.framework.proxy.ControllerAspect;
import org.smart4j.framework.proxy.Proxy;
import org.smart4j.framework.proxy.ProxyManager;
import org.smart4j.framework.util.ReflectionUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppMain {
	private static final Logger logger = LoggerFactory.getLogger(AppMain.class);
	public static void main(String[] args) {

		ApplicationContext context=new ClassPathXmlApplicationContext("/app.xml");
		Greeting  greetingImpl=(Greeting) context.getBean("greetingImpl");
		greetingImpl.sayHello("Jack");
	/*	greetingImpl.goodMorning("Jack");
		Apology  apology=(Apology)greetingImpl;
		apology.apology("Jack");*/
		/*List<Proxy> proxyList=new ArrayList<Proxy>();
		proxyList.add(new PerformanceAspect());
		proxyList.add(new ControllerAspect());
		ProxyManager manager=new ProxyManager();
		GreetingImpl g=	manager.createProxy(GreetingImpl.class, proxyList);
		logger.info("dfasd");
		g.goodMorning("Jame");
		g.sayHello("Jack");
		HelperLoader.init();*/
	}

}
