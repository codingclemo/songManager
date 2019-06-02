package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import swt6.spring.worklog.domain.Album;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.domain.Song;
import swt6.spring.worklog.logic.SongManagerFacade;

import java.util.*;

@SuppressWarnings("Dulicates")
@Controller
public class SongController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private SongManagerFacade songManager;

    @RequestMapping("/songs")
    public String list(Model model) {
        List<Song> songs = new ArrayList<>(songManager.findAllSongs());
        Collections.sort(songs, Comparator.comparing(Song::getTitle));
        model.addAttribute("songs", songs);

        return "allSongs";
    }


    @RequestMapping("/albums/{albumId}/songs")
    public String list(@PathVariable("albumId")long albumId, Model model) {

        Album album = songManager.findAlbumById(albumId);
        List<Song> songs = new ArrayList<>(album.getSongs());
        Collections.sort(songs, Comparator.comparing(Song::getTitle));

        model.addAttribute("album", album);
        model.addAttribute("songs", songs);

        return "songList";
    }

    @RequestMapping(value="/albums/{albumId}/songs/new", method = RequestMethod.GET)
    public String initNew(@PathVariable("albumId") Long albumId, Model model) {
        Song song = new Song("",0L,"");
        Album album = songManager.findAlbumById(albumId);
        album.addSong(song);
        model.addAttribute("song", song);
        //model.addAttribute("genreName", "asdasd");

        return "song";
    }

    @RequestMapping(value="/albums/{albumId}/songs/new", method = RequestMethod.POST)
    public String processNew(@PathVariable("albumId") Long albumId,
                             @ModelAttribute("song") Song song,
                             //@ModelAttribute("genreName") String genreName,
                             BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "song";
        }
        else {
            //song = songManager.findSongById(song.getId());
            song = songManager.syncSong(song);
            song.addAlbum(songManager.findAlbumById(albumId));
            //song = addGenre(song, genreName);
            song = songManager.syncSong(song);
            logger.debug("added/updated song {}", song);
            return "redirect:/albums/{albumId}/songs";
        }
    }

    private String internalProcessUpdate(Long albumId, Song song, /* String genreName, */ BindingResult result) {
        if (result.hasErrors()) {
            return "song";
        }
        else {
            //song = songManager.findSongById(song.getId());

            song.addAlbum(songManager.findAlbumById(albumId));
            //song = addGenre(song, genreName);
            song = songManager.syncSong(song);
            logger.debug("added/updated song {}", song);
            return "redirect:/albums/{albumId}/songs";
        }
    }

    private Song addGenre(Song song, String genreName) {
        Collection<Genre> genres = songManager.findAllGenres();
        boolean found = false;
        for (Genre genre : genres) {
            if (genre.getName().equals(genreName)) {
                found = true;
                song.addGenre(genre);
                break;
            }
        }

        if (!found) {
            Genre genre = new Genre(genreName);
            song.addGenre(genre);
        }
        return song;
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/update", method = RequestMethod.GET)
    public String initUpdate(@PathVariable("albumId") Long albumId,
                             @PathVariable("songId") Long songId,
                             Model model) {

        Song song = songManager.findSongById(songId);

//        String genreName = "";
//        if (song.getGenre() != null)
//            genreName = song.getGenre().getName();

        model.addAttribute("song", song);
        //model.addAttribute("genreName", genreName);
        return "song";
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/update", method = RequestMethod.POST)
    public String processUpdate(@PathVariable("albumId") Long albumId,
                                @ModelAttribute("song") Song song,
//                                @ModelAttribute("genreName") String genreName,
                                BindingResult result) {
        return internalProcessUpdate(albumId, song, /* genreName, */ result);

    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/remove", method = RequestMethod.GET)
    public String initRemove(@PathVariable("albumId") Long albumId,
                             @PathVariable("songId") Long songId,
                             Model model) {

        Song song = songManager.findSongById(songId);
        model.addAttribute("song", song);
        return "songRemove";
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/remove", method = RequestMethod.POST)
    public String processRemove(@PathVariable("albumId") Long albumId,
                                @ModelAttribute("song") Song song) {
        Album album = songManager.findAlbumById(albumId);
        song = songManager.findSongById(song.getId());

        song.removeAlbum(album);
        //album = songManager.syncAlbum(album);
        song = songManager.syncSong(song);

//        album.removeSong(song);
//        album = songManager.syncAlbum(album);
        return "redirect:/albums/{albumId}/songs";
    }
}
