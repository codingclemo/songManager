package swt6.spring.basics.hello;

public class GreetingServiceImpl implements GreetingService{

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void sayHello() {
        System.out.println(message);
    }
}
