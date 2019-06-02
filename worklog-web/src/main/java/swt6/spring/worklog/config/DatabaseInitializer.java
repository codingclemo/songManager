package swt6.spring.worklog.config;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import swt6.spring.worklog.domain.*;
import swt6.spring.worklog.logic.SongManagerFacade;
import swt6.spring.worklog.logic.WorkLogFacade;

@Component
//@Profile("dev")
@SuppressWarnings("Duplicates")
public class DatabaseInitializer implements CommandLineRunner {

  @Autowired
  private WorkLogFacade workLog;

  @Autowired
  private SongManagerFacade songManager;

  @Override
  public void run(String... args) throws Exception {

    Song song1;
    Song song2;
    Song song3;

    song1 = new Song("Song1", (long) (1*60 + 11),"Interpret1");
    song2 = new Song("Song2", (long) (2*60 + 22),"Interpret2");
    song3 = new Song("Song3", (long) (3*60 + 33),"Interpret3");

    Genre genre1 = new Genre("Genre1");
    Genre genre2 = new Genre("Genre2");
    Genre genre3 = new Genre("Genre3");

    song1.addGenre(genre1);
    song2.addGenre(genre2);
    song3.addGenre(genre3);


    Album album1 = new Album("Album1",2001);
    Album album2 = new Album("Album2",2002);
    Album album3 = new Album("Album3",2003);

    song1.addAlbum(album1);
    song2.addAlbum(album1);
    song3.addAlbum(album1);

    song2.addAlbum(album2);
    song3.addAlbum(album3);

    album1 = songManager.syncAlbum(album1);
    album2 = songManager.syncAlbum(album2);
    album3 = songManager.syncAlbum(album3);


    // can be deleted later
    Employee empl1 = new Employee("Sepp", "Forcher", LocalDate.of(1935, 12, 12));
    empl1.addLogbookEntry(new LogbookEntry("Jour Fixe", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 10, 0)));
    empl1.addLogbookEntry(new LogbookEntry("Analyse", LocalDateTime.of(2018, 3, 1, 10, 0), LocalDateTime.of(2018, 3, 1, 13, 45)));
    empl1.addLogbookEntry(new LogbookEntry("Implementierung", LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30)));
    workLog.syncEmployee(empl1);
    
    Employee empl2 = new Employee("Alfred", "Kunz", LocalDate.of(1944, 8, 10));
    empl2.addLogbookEntry(new LogbookEntry("Jour Fixe", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 10, 0)));
    empl2.addLogbookEntry(new LogbookEntry("Unit-Test schreiben", LocalDateTime.of(2018, 3, 1, 10, 15), LocalDateTime.of(2018, 3, 1, 14, 30)));
    empl2.addLogbookEntry(new LogbookEntry("Integations-Tests wiederholen", LocalDateTime.of(2018, 3, 1, 14, 30), LocalDateTime.of(2018, 3, 1, 16, 00)));
    workLog.syncEmployee(empl2);
    
    Employee empl3 = new Employee("Sigfried", "Hinz", LocalDate.of(1954, 5, 3));
    empl3.addLogbookEntry(new LogbookEntry("Jour Fixe", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 10, 0)));
    empl3.addLogbookEntry(new LogbookEntry("Benutzerdoku aktualisieren", LocalDateTime.of(2018, 3, 1, 8, 15), LocalDateTime.of(2018, 3, 1, 16, 30)));
    workLog.syncEmployee(empl3);
  }
}