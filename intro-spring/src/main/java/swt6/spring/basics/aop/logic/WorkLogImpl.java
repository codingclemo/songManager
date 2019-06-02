package swt6.spring.basics.aop.logic;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Component("workLog")
public class WorkLogImpl implements WorkLogFacade {

	private Map<Long, Employee> employees = new HashMap<Long, Employee>();

	private void init() {
		employees.put(1L, new Employee(1L, "Bill", "Gates"));
		employees.put(2L, new Employee(2L, "James", "Goslin"));
		employees.put(3L, new Employee(3L, "Bjarne", "Stroustrup"));
	}

	public WorkLogImpl() {
		init();
	}

	public Employee findEmployeeById(Long id) throws EmployeeIdNotFoundException {
		System.out.println("Test In METHOD");
		if (employees.get(id) == null)
			throw new EmployeeIdNotFoundException();
		return employees.get(id);
	}

	public List<Employee> findAllEmployees() {
		return new ArrayList<Employee>(employees.values());
	}
}
