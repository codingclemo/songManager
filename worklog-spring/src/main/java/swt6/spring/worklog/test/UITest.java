package swt6.spring.worklog.test;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.ui.UIProcessFacade;

public class UITest {

  private static void uiTest(String configFile) {

    // create domain objects

    Employee empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
    Employee empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
    Employee empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));

    LogbookEntry entry1 = new LogbookEntry("Analyse", 
    	LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 13, 45));
    LogbookEntry entry2 = new LogbookEntry("Implementierung",
    	LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));
    LogbookEntry entry3 = new LogbookEntry("Testen",
    	LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30));

    try (AbstractApplicationContext appCtx =
        new ClassPathXmlApplicationContext(configFile)) {

    	UIProcessFacade uiComp = appCtx.getBean(UIProcessFacade.class);

      // adding employees
    	
      printTitle("saveEmployees", 60, '-');
      uiComp.saveEmployees(empl1, empl2, empl3);

      // adding LogbookEntries
      
      empl1.addLogbookEntry(entry1);
      empl1.addLogbookEntry(entry2);
      empl2.addLogbookEntry(entry3);

      printTitle("saveEmployees", 60, '-');
      uiComp.saveEmployees(empl1, empl2);

      printTitle("findAll", 60, '-');
      uiComp.findAll();
    }
  }

  // run with 
  // --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED
  public static void main(String[] args) {
//  	printSeparator(60); printTitle("UITest (Hibernate)", 60); printSeparator(60);
//    uiTest("swt6/spring/worklog/test/applicationContext-hibernate.xml");

  	printSeparator(60); printTitle("UITest (JPA)", 60); printSeparator(60);
    uiTest("swt6/spring/worklog/test/applicationContext-jpa1.xml");
  }
}
