package swt6.spring.basics.aop.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.basics.aop.logic.EmployeeIdNotFoundException;
import swt6.spring.basics.aop.logic.WorkLogFacade;

public class AOPTest {
  // testAOP
  private static void testAOP(String configFileName) {
    try (AbstractApplicationContext factory = new
            ClassPathXmlApplicationContext(configFileName)) {
      System.out.println("----------- TraceAdvice, ProfilingAdvice -----------");
      WorkLogFacade workLog = factory.getBean("workLog", WorkLogFacade.class);
      try {
        workLog.findAllEmployees();
        System.out.println("Test OUT BEFORE METHOD");
        workLog.findEmployeeById(3L);
        System.out.println("Test OUT AFTER METHOD");
        //workLog.findEmployeeById(5L);
      } catch (EmployeeIdNotFoundException e) {

      }
    }
  }
//

  public static void main(String[] args) {
    System.out.println("=============== testAOP (config based) ===============");
    testAOP("swt6/spring/basics/aop/test/applicationConfig-xml-config.xml");

    // System.out.println("============= testAOP (annotation based) =============");
    // testAOP("<< insert spring config file here >>");
  }

}
