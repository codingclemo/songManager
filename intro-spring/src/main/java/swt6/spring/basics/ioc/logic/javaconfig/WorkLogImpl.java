package swt6.spring.basics.ioc.logic.javaconfig;

import org.springframework.stereotype.Component;
import swt6.spring.basics.ioc.domain.Employee;
import swt6.spring.basics.ioc.logic.WorkLogFacade;
import swt6.spring.basics.ioc.util.Log;
import swt6.spring.basics.ioc.util.Logger;
import swt6.spring.basics.ioc.util.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;

public class WorkLogImpl implements WorkLogFacade {
  private Map<Long, Employee> employees = new HashMap<Long, Employee>();

  // Version 1:
  //private ConsoleLogger logger = new ConsoleLogger();

  // Version 2:
  //private Logger logger = new ConsoleLogger();

  // Version 3:
  //private Logger logger = LoggerFactory.getLogger("file");

  // Version 4: load from worklog.properties
  // private Logger logger;

  // Version 5: spring annotation-config
  //  @Autowired(required = true)
  //  @Log(LoggerType.STANDARD)
  // private Logger logger;

  // Version 6: jsr330 annotation
  @Inject
  @Log
  private Logger logger;

  private void initLogger() {
    Properties props = new Properties();
   
    try {
      ClassLoader cl = this.getClass().getClassLoader();
      props.load(cl.getResourceAsStream(
                 "swt6/spring/basics/ioc/logic/worklog.properties"));
    }
    catch (IOException e) {
      e.printStackTrace();
    }

    String type = props.getProperty("loggerType", "consoleLogger");
    logger = LoggerFactory.getLogger(type);

  }
  
  private void init() {
    employees.put(1L, new Employee(1L, "Bill", "Gates"));
    employees.put(2L, new Employee(2L, "James", "Goslin"));
    employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
  }

  public WorkLogImpl(Logger logger) {
    init();
    this.logger = logger;
  }
  
  public WorkLogImpl() {
    initLogger();
    init();
  }

  public void setLogger(Logger logger) {
    this.logger = logger;
  }

  @Override
  public Employee findEmployeeById(Long id) {
    Employee employee = employees.get(id);
    logger.log("findEmployeeById(" + id + ") --> " + ((employee != null) ? employee.toString() : "<null>"));
    return employees.get(id);
  }

  @Override
  public List<Employee> findAllEmployees() {
    logger.log("findAllEmployees");
    return new ArrayList<Employee>(employees.values());
  }
}
