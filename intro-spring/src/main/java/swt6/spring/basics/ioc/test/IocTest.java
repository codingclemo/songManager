package swt6.spring.basics.ioc.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.ioc.WorkLogConfig;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.logic.factorybased.WorkLogImpl;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class IocTest {

    private static void testXmlConfig() {
        try(AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
                "swt6/spring/basics/ioc/test/applicationContext-xml-config.xml"
        )) {
           System.out.println( "***> workLog-setter-injected" );
            WorkLogFacade workLog1 = factory.getBean( "workLog-setter-injected", WorkLogFacade.class );
            workLog1.findAllEmployees();
            workLog1.findEmployeeById( 3L );

            System.out.println( "***> workLog-constructor-injected" );
            WorkLogFacade workLog2 = factory.getBean( "workLog-constructor-injected", WorkLogFacade.class );
            workLog2.findAllEmployees();
            workLog2.findEmployeeById( 3L );
        }
    }

    private static void testAnnotationConfig() {
        try(AbstractApplicationContext factory =
                    new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-annotation-config.xml"
        )) {
            WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);
            workLog.findAllEmployees();
            workLog.findEmployeeById(3L);
        }
    }

    private static void testComponentScanConfig() {
        try(AbstractApplicationContext factory =
                    new ClassPathXmlApplicationContext("swt6/spring/basics/ioc/test/applicationContext-scan-config.xml"
                    )) {
            WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);
            workLog.findAllEmployees();
            workLog.findEmployeeById(3L);
        }
    }

    private static void testJavaConfig() {

        try (AbstractApplicationContext factory =
                new AnnotationConfigApplicationContext(WorkLogConfig.class)) {
            WorkLogFacade workLog = factory.getBean(WorkLogFacade.class);
            workLog.findAllEmployees();
            workLog.findEmployeeById(3L);
        }

    }

    private static void testSimple( ) {
        WorkLogImpl worklog = new WorkLogImpl();
        worklog.findAllEmployees();
        worklog.findEmployeeById(3L);
    }

    public static void main(String[] args) {
        printTitle("testSimple", 60);
        testSimple();
        printSeparator(60);
        printTitle("testXmlConfig", 60);
        testXmlConfig();
        printSeparator(60);
        printTitle("testAnnotationConfig", 60);
        testAnnotationConfig();
        printSeparator(60);
        printTitle("testComponentScanConfig", 60);
        testComponentScanConfig();
        printSeparator(60);
        printTitle("testJavaConfig", 60);
        testJavaConfig();
    }
}