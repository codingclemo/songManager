package swt6.spring.basics.ioc.util;


import org.springframework.stereotype.Component;

// allow for spring component scan to be detected
@Component
@Log(LoggerType.STANDARD)
public class ConsoleLogger implements Logger {

    private String prefix = "Log";

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void log(String message) {
        System.out.printf("%s: %s%n", prefix, message);
    }
}
