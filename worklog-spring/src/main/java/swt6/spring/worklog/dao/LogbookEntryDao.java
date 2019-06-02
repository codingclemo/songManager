package swt6.spring.worklog.dao;

import java.util.List;
import org.springframework.dao.DataAccessException;
import swt6.spring.worklog.domain.LogbookEntry;

public interface LogbookEntryDao extends GenericDao<LogbookEntry, Long> {
  List<LogbookEntry> findByEmployee(Long employeeId)
      throws DataAccessException;
  void deleteById(Long id) throws DataAccessException;
}
