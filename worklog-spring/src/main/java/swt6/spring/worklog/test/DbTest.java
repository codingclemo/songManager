package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.util.DbScriptRunner;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.xml.transform.sax.SAXSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static swt6.util.PrintUtil.printSeparator;
import static swt6.util.PrintUtil.printTitle;

public class DbTest {

  private static void createSchema(DataSource ds, String ddlScript) {
    try {
      DbScriptRunner scriptRunner = new DbScriptRunner(ds.getConnection());
      InputStream is = DbTest.class.getClassLoader().getResourceAsStream(ddlScript);
      if (is == null) throw new IllegalArgumentException(
        String.format("File %s not found in classpath.", ddlScript));
      scriptRunner.runScript(new InputStreamReader(is));
    }
    catch (SQLException | IOException e) {
      e.printStackTrace();
      return;
    }
  }

  private static void testJdbc() {

    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "swt6/spring/worklog/test/applicationContext-jdbc.xml")) {

      printTitle("create schema", 60, '-');
      createSchema(factory.getBean("dataSource", DataSource.class),
        "swt6/spring/worklog/test/CreateWorklogDbSchema.sql");

      EmployeeDao emplDao = factory.getBean("employeeDaoJdbc", EmployeeDao.class);

      printTitle("insert employee", 60, '-');
      Employee empl1 = new Employee("Josefine", "Feichtlbauer",
              LocalDate.of(1970, 10, 26));
      emplDao.insert(empl1);
      System.out.println("empl1 = " + empl1);

      printTitle("update employee", 60, '-');
      empl1.setFirstName("Jaquira");
      empl1 = emplDao.merge(empl1);
      System.out.println("empl1 = " + empl1);

      printTitle("find employee", 60, '-');
      Employee empl2 = emplDao.findById(1L);
      System.out.println("empl2 = " + empl2);
      empl2 = emplDao.findById(100L);
      System.out.println("empl2 = " + empl2);

      printTitle("find all employees", 60, '-');
      emplDao.findAll();

    }
  }

  @SuppressWarnings("Duplicates")
  private static void testJpa() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

      // TODO: ...
      EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);
      EmployeeDao emplDao = factory.getBean("emplDaoJpa", EmployeeDao.class);

      printTitle("insert employee", 60, '-');
      Employee empl1 = new Employee("Josefine", "Feichtlbauer",
              LocalDate.of(1970, 10, 26));
      JpaUtil.beginTransaction(emFactory);

      emplDao.insert(empl1);
      System.out.println("empl1 = " + empl1);

      printTitle("update employee", 60, '-');
      empl1.setFirstName("Jaquira");
      empl1 = emplDao.merge(empl1);
      System.out.println("empl1 = " + empl1);

      Long id = empl1.getId();
      JpaUtil.commitTransaction(emFactory);

      printTitle("find employee", 60, '-');

      JpaUtil.beginTransaction(emFactory);
      Employee empl2 = emplDao.findById(1L);

      System.out.println("empl2 = " + empl2);
      empl2 = emplDao.findById(100L);
      System.out.println("empl2 = " + empl2);

      printTitle("find all employees", 60, '-');
      emplDao.findAll().forEach(System.out::println);

      JpaUtil.commitTransaction(emFactory);
    }
  }

  private static void testSpringData() {
    try (AbstractApplicationContext factory = new ClassPathXmlApplicationContext(
      "swt6/spring/worklog/test/applicationContext-jpa1.xml")) {

      EntityManagerFactory emFactory = factory.getBean(EntityManagerFactory.class);

      JpaUtil.executeInTransaction(emFactory, () -> {
        EmployeeRepository emplRepo = JpaUtil.getJpaRepository(emFactory, EmployeeRepository.class);

        Employee empl1 = new Employee("Josef", "Himmelbauer",
                LocalDate.of(1950, 1, 1));
        Employee empl2 = new Employee("Karl", "Malden",
                LocalDate.of(1940, 5, 3));

        printTitle("save employee", 60, '-');
        empl1 = emplRepo.save(empl1);
        empl2 = emplRepo.save(empl2);
        emplRepo.flush();

        printTitle("update employee", 60, '-');
        empl1.setFirstName("Himmelbauer-Huber");
        empl1 = emplRepo.save(empl1);

        Optional<Employee> emplOpt = emplRepo.findByLastName("Malden");
        if (emplOpt.isPresent())
          System.out.println(emplOpt.get());

      }); //kein flush weil db zugemacht wird

    }
  }

  // Java 9: run with 
  // --add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang
  // .invoke=ALL-UNNAMED
  public static void main(String[] args) {

    printSeparator(60);
    printTitle("testJDBC", 60);
    printSeparator(60);
    testJdbc();

    printSeparator(60);
    printTitle("testJpa", 60);
    printSeparator(60);
    testJpa();

    printSeparator(60);
    printTitle("testSpringData", 60);
    printSeparator(60);
    testSpringData();
  }
}
