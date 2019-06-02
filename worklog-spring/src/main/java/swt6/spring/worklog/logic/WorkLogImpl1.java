package swt6.spring.worklog.logic;

import org.springframework.transaction.annotation.Transactional;
import swt6.spring.worklog.dao.EmployeeDao;
import swt6.spring.worklog.dao.LogbookEntryDao;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;

import java.util.List;

@Transactional
public class WorkLogImpl1 implements WorkLogFacade {

    private EmployeeDao employeeDao;
    private LogbookEntryDao logbookEntryDao;

    // ================== DAO Setters ==========================
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public void setLogbookEntryDao(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }
    // ========================================================

    @Override
    public Employee syncEmployee(Employee employee) {
        return employeeDao.merge(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeById(Long id) {
        return employeeDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeDao.findAll();
    }

    @Override
    public LogbookEntry syncLogbookEntry(LogbookEntry entry) {
        return logbookEntryDao.merge(entry);
    }

    @Override
    @Transactional(readOnly = true)
    public LogbookEntry findLogbookEntryById(Long id) {
        return logbookEntryDao.findById(id);
    }

    @Override
    public void deleteLogbookEntryById(Long id) {
        logbookEntryDao.deleteById(id);
    }
}
