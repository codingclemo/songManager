package swt6.spring.basics.ioc.util;

public class LoggerFactory {

    public static Logger getLogger(String type) {
        if (type.equals("file")) {
            return new FileLogger("log.txt");
        } else {
            return new ConsoleLogger();
        }
    }
}
