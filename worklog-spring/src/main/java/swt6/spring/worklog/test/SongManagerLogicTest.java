package swt6.spring.worklog.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.domain.Song;
import swt6.spring.worklog.logic.SongManagerFacade;
import swt6.spring.worklog.logic.WorkLogFacade;

import javax.persistence.EntityManagerFactory;
import java.time.Duration;

import static swt6.util.PrintUtil.printTitle;

@SuppressWarnings("Duplicates")
public class SongManagerLogicTest {

    private static Song song1;
    private static Song song2;
    private static Song song3;

    public static void main(String[] args) {
        try (AbstractApplicationContext appCtx =
                     new ClassPathXmlApplicationContext(
                             "swt6/spring/worklog/test/applicationContext-jpa2.xml")) {

            EntityManagerFactory emFactory = appCtx.getBean(EntityManagerFactory.class);
//            final WorkLogFacade workLog = appCtx.getBean("workLog", WorkLogFacade.class);
            final SongManagerFacade songManager = appCtx.getBean("songManager", SongManagerFacade.class);

            printTitle("testSaveSongs", 60, '-');
            testSaveSong(songManager);

            printTitle("testAddGenre", 60, '-');
            testAddGenre(songManager);

            printTitle("testAddAlbum", 60, '-');
            testAddAlbum(songManager);
        }
    }

    private static void testAddAlbum(SongManagerFacade songManager) {
        Album album1 = new Album("Album1",2001);
        Album album2 = new Album("Album2",2002);
        Album album3 = new Album("Album3",2003);

        song1.addAlbum(album1);
        song2.addAlbum(album1);
        song3.addAlbum(album1);

        song2.addAlbum(album2);
        song3.addAlbum(album3);

        song1 = songManager.syncSong(song1);
        song2 = songManager.syncSong(song2);
        song3 = songManager.syncSong(song3);
    }

    private static void testAddGenre(SongManagerFacade songManager) {
        Genre genre1 = new Genre("Genre1");
        Genre genre2 = new Genre("Genre2");
        Genre genre3 = new Genre("Genre3");

        song1.addGenre(genre1);
        song2.addGenre(genre2);
        song3.addGenre(genre3);

        song1 = songManager.syncSong(song1);
        song2 = songManager.syncSong(song2);
        song3 = songManager.syncSong(song3);
    }

    private static void testSaveSong(SongManagerFacade songManager) {
        song1 = new Song("Song1", (long) (1*60 + 11),"Interpret1");
        song2 = new Song("Song2", (long) (2*60 + 22),"Interpret2");
        song3 = new Song("Song3", (long) (3*60 + 33),"Interpret3");

        song1 = songManager.syncSong(song1);
        song2 = songManager.syncSong(song2);
        song3 = songManager.syncSong(song3);
    }


}
