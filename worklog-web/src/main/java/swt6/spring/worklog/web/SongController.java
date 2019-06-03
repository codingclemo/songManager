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
    public String listAllSongs(Model model) {
        List<Song> songs = new ArrayList<>(songManager.findAllSongs());
        Collections.sort(songs, Comparator.comparing(Song::getTitle));
        model.addAttribute("songs", songs);

        return "allSongs";
    }


    @RequestMapping("/albums/{albumId}/songs")
    public String listAlbumSongs(@PathVariable("albumId")long albumId, Model model) {

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

        List<Genre> genres = songManager.findAllGenres();
        Collections.sort(genres, Comparator.comparing(Genre::getName));
        model.addAttribute("genres", genres);

        return "song";
    }

    @RequestMapping(value="/albums/{albumId}/songs/new", method = RequestMethod.POST)
    public String processNew(@PathVariable("albumId") Long albumId,
                             @ModelAttribute("song") Song song,
                             BindingResult result, Model model) {

        return internalProcessUpdate(albumId, song, result);
    }

    private String internalProcessUpdate(Long albumId, Song song, BindingResult result) {
        if (result.hasErrors()) {
            return "song";
        }
        else {
            logger.debug("song" + song);
            addGenre(song);
            logger.debug("song" + song);
            //song.addAlbum(songManager.findAlbumById(albumId));
            song = songManager.syncSong(song);
            logger.debug("added/updated song {}", song);
            return "redirect:/albums/{albumId}/songs";
        }
    }

    private Song addGenre(Song song) {
        Collection<Genre> genres = songManager.findAllGenres();
        for (Genre g : genres) {
            if (g.getName().equals(song.getGenre().getName())) {
                song.addGenre(g);
                break;
            }
        }
        return song;
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/update", method = RequestMethod.GET)
    public String initUpdate(@PathVariable("albumId") Long albumId,
                             @PathVariable("songId") Long songId,
                             Model model) {

        List<Genre> genres = songManager.findAllGenres();
        Collections.sort(genres, Comparator.comparing(Genre::getName));
        Song song = songManager.findSongById(songId);
        model.addAttribute("song", song);
        model.addAttribute("genres", genres);

        return "song";
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/update", method = RequestMethod.POST)
    public String processUpdate(@PathVariable("albumId") Long albumId,
                                @ModelAttribute("song") Song song,
                                @ModelAttribute("genre") Genre genre,
                                BindingResult result) {
        logger.debug("genre: " + genre);
        return internalProcessUpdate(albumId, song, result);
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/remove", method = RequestMethod.GET)
    public String initRemoveFromAlbum(@PathVariable("albumId") Long albumId,
                             @PathVariable("songId") Long songId,
                             Model model) {

        Song song = songManager.findSongById(songId);
        model.addAttribute("song", song);
        return "songRemove";
    }

    @RequestMapping(value="/albums/{albumId}/songs/{songId}/remove", method = RequestMethod.POST)
    public String processRemoveFromAbum(@PathVariable("albumId") Long albumId,
                                @ModelAttribute("song") Song song) {
        Album album = songManager.findAlbumById(albumId);
        song = songManager.findSongById(song.getId());
        song.removeAlbum(album);
        song = songManager.syncSong(song);
        return "redirect:/albums/{albumId}/songs";
    }

    @RequestMapping(value="/songs/{songId}/delete", method = RequestMethod.GET)
    public String processDeleteSong(@PathVariable("songId") Long songId) {
        songManager.deleteSong(songId);
        return "redirect:/songs";
    }
}
