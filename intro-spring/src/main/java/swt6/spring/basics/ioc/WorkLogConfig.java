package swt6.spring.basics.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.javaconfig.WorkLogImpl;
import swt6.spring.basics.ioc.util.*;

@Configuration
@ComponentScan(basePackageClasses = WorkLogConfig.class)
public class WorkLogConfig {

//    @Bean @Log(LoggerType.STANDARD)
//    public Logger consoleLogger() {
//        return new ConsoleLogger();
//    }
//
//    @Bean @Log(LoggerType.FILE)
//    public Logger fileLogger() {
//        return new FileLogger();
//    }

    @Bean
    public WorkLogFacade workLog() {
        return new WorkLogImpl();
    }

}
