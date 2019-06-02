package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
}
