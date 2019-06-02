package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import swt6.spring.worklog.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByLastName(@Param("name") String lastName);

    @Query("select e from Employee e where e.lastName like %:substr%")
    List<Employee> findByLastNameContaining(@Param("substr") String substr);
}
