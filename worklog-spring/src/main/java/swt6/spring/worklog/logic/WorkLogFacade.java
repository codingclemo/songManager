package swt6.spring.worklog.logic;

import java.util.List;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

public interface WorkLogFacade {
  public Employee       syncEmployee(Employee employee);
  public Employee       findEmployeeById(Long id);
  public List<Employee> findAllEmployees();
  
  public LogbookEntry   syncLogbookEntry(LogbookEntry entry);
  public LogbookEntry   findLogbookEntryById(Long id);
  public void           deleteLogbookEntryById(Long id);
}