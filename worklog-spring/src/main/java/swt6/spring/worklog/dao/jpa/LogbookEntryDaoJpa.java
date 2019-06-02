package swt6.spring.worklog.dao.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.dao.LogbookEntryDao;
import swt6.spring.worklog.domain.LogbookEntry;

@Repository
public class LogbookEntryDaoJpa  implements LogbookEntryDao  {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void insert(LogbookEntry e) throws DataAccessException {
    em.merge(e);
  }

  @Override
  public List<LogbookEntry> findAll() throws DataAccessException {
    return em.createQuery("select le from LogbookEntry le", LogbookEntry.class).getResultList();
  }

  @Override
  public List<LogbookEntry> findByEmployee(Long employeeId) throws DataAccessException {
    return em.createQuery("select le from LogbookEntry le where le.employee.id=:id", LogbookEntry.class)
             .setParameter("id", employeeId)
             .getResultList();
  }

  @Override
  public LogbookEntry findById(Long id) throws DataAccessException {
    return em.find(LogbookEntry.class, id);
  }

  @Override
  public void deleteById(Long id) throws DataAccessException {
    LogbookEntry entry = em.find(LogbookEntry.class, id);
    entry.detachEmployee();
    em.remove(entry); // not necessary if orphanRemoval=true
  }

  @Override
  public LogbookEntry merge(LogbookEntry entry) throws DataAccessException {
    return em.merge(entry);
  }
}
