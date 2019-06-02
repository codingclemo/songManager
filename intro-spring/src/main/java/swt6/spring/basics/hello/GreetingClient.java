package swt6.spring.basics.hello;

import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GreetingClient {

    public static void main(String[] args) {

        try(AbstractXmlApplicationContext factory =
                    new ClassPathXmlApplicationContext("swt6/spring/basics/hello/GreetingService.xml")) {
            GreetingService bean = factory.getBean("greetingService", GreetingService.class);
            bean.sayHello();
        }



    }
}
