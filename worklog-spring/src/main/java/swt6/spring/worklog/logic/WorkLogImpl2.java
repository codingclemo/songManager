package swt6.spring.worklog.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeRepository;
import swt6.spring.worklog.dao.LogbookEntryRepository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

import java.util.List;

@Component("workLog")
@Primary //if two beans with same name "workLog" exist in application contest use this one
@Transactional
public class WorkLogImpl2 implements WorkLogFacade {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LogbookEntryRepository logbookEntryRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void setLogbookEntryRepository(LogbookEntryRepository logbookEntryRepository) {
        this.logbookEntryRepository = logbookEntryRepository;
    }

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id).get();
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryRepository.saveAndFlush(entry);
    }

    @Override
    public LogbookEntry findLogbookEntryById(Long id) {
        return logbookEntryRepository.findById(id).get();
    }

    @Override
    public void deleteLogbookEntryById(Long id) {
        logbookEntryRepository.deleteById(id);
    }
}
