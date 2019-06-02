package swt6.spring.worklog.test;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.logic.WorkLogFacade;
import swt6.util.JpaUtil;

@SuppressWarnings("Duplicates")
public class LogicTest {

  private static Employee empl1;
  private static Employee empl2;
  private static Employee empl3;

  private static void testSaveEmployee(WorkLogFacade workLog) {
    empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
    empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
    empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));

    empl1 = workLog.syncEmployee(empl1);
    empl2 = workLog.syncEmployee(empl2);
    empl3 = workLog.syncEmployee(empl3);
  }

  private static void testAddLogbookEntry(WorkLogFacade workLog) {
    LogbookEntry entry1 = new LogbookEntry("Analyse",
            LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 11, 30));
    LogbookEntry entry2 = new LogbookEntry("Implementierung",
            LocalDateTime.of(2018, 3, 1, 11, 30), LocalDateTime.of(2018, 3, 1, 16, 30));
    LogbookEntry entry3 = new LogbookEntry("Testen",
            LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));

    empl1.addLogbookEntry(entry1);
    empl1.addLogbookEntry(entry2);
    empl2.addLogbookEntry(entry3);

    empl1 = workLog.syncEmployee(empl1);
    empl2 = workLog.syncEmployee(empl2);
  }

  private static void testFindAll(WorkLogFacade workLog) {
    for (Employee e : workLog.findAllEmployees()) {
      System.out.println(e);
      e.getLogbookEntries().forEach(entry ->
              System.out.println("   " + entry.getId() + ": " + entry));
    }
  }

  private static void testBusinessLogicWithJpaDaos() {
    try (AbstractApplicationContext appCtx =
                 new ClassPathXmlApplicationContext(
                         "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

      final WorkLogFacade workLog = appCtx.getBean("workLog", WorkLogFacade.class);
      EntityManagerFactory emFactory = appCtx.getBean(EntityManagerFactory.class);

      printTitle("testSaveEmployee", 60, '-');
      testSaveEmployee(workLog);

      printTitle("testAddLogbookEntry", 60, '-');
      testAddLogbookEntry(workLog);

      /*printTitle("testFindByIds", 60, '-');
      testFindById(worklog); */

      printTitle("testFindAll", 60, '-');
      // version 1: fails due to closed session
      //testFindAll(workLog);

      // version 2: keep session open
      /*JpaUtil.openEntityManager(emFactory);
      testFindAll(workLog);
      JpaUtil.closeEntityManager(emFactory);*/

      // version 3: lambda
      JpaUtil.executeInOpenEntityManager(emFactory, () -> testFindAll(workLog));
    }
  }

  private static void testBusinessLogicWithSpringDataRepositories() {
    try (AbstractApplicationContext appCtx =
                 new ClassPathXmlApplicationContext(
                         "swt6/spring/worklog/test/applicationContext-jpa2.xml")) {

      EntityManagerFactory emFactory = appCtx.getBean(EntityManagerFactory.class);
      final WorkLogFacade workLog = appCtx.getBean("workLog", WorkLogFacade.class);

      printTitle("testSaveEmployee", 60, '-');
      testSaveEmployee(workLog);

      printTitle("testAddLogbookEntry", 60, '-');
      testAddLogbookEntry(workLog);

      /*printTitle("testFindByIds", 60, '-');
      testFindById(worklog); */

      printTitle("testFindAll", 60, '-');
      // version 1: fails due to closed session
      //testFindAll(workLog);

      // version 2: keep session open
      /*JpaUtil.openEntityManager(emFactory);
      testFindAll(workLog);
      JpaUtil.closeEntityManager(emFactory);*/

      // version 3: lambda
      JpaUtil.executeInOpenEntityManager(emFactory, () -> testFindAll(workLog));
    }

  }

  // run with 
  // --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED
  public static void main(String[] args) {
    printSeparator(60);
    printTitle("testBusinessLogicWithJpaDaos", 60);
    printSeparator(60);
    testBusinessLogicWithJpaDaos();
    
  	printSeparator(60);
  	printTitle("testBusinessLogicWithSpringDataRepositories", 60); 
  	printSeparator(60);
    testBusinessLogicWithSpringDataRepositories();
  }
}
