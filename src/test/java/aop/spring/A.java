package aop.spring;

public class A {
	int a;
	public void aaa(){
		System.out.println("A"+a);
	}
	public A(){
		aaa();
	}
	public static void main(String[] args) {
		new B();
	}

}
class B extends A{
	int a=1;
	public void aaa(){
		System.out.println("B"+a);
		}

}