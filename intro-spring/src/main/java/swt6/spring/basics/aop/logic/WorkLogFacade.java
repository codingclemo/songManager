package swt6.spring.basics.aop.logic;

import java.util.List;

public interface WorkLogFacade {
  public Employee findEmployeeById(Long id) throws EmployeeIdNotFoundException;
  public List<Employee> findAllEmployees();
}