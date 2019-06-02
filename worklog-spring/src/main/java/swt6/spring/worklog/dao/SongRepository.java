package swt6.spring.worklog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
