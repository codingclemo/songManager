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
import swt6.spring.worklog.domain.Song;
import swt6.spring.worklog.logic.SongManagerFacade;
import swt6.spring.worklog.logic.WorkLogFacade;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class AlbumController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private SongManagerFacade songManager;

    @RequestMapping("/albums")
    public String list(Model model) {
        List<Album> albums = this.songManager.findAllAlbums();
        Collections.sort(albums, Comparator.comparing(Album::getTitle));
        model.addAttribute("albums", albums);
        logger.debug("albums: " + albums);
        return "albumList";
    }

    @RequestMapping(value="/albums/new", method = RequestMethod.GET)
    public String initNew(Model model) {
        Album album = new Album("", 1990);
        model.addAttribute("album", album);
        return "album";
    }

    @RequestMapping(value="/albums/new", method = RequestMethod.POST)
    public String processNew(@ModelAttribute("album") Album album, BindingResult result, Model model) {
        return internalProcessUpdate(album, result);
    }

    private String internalProcessUpdate(Album album, BindingResult result) {
        if (result.hasErrors()) {
            return "album";
        }
        else {
            album = songManager.syncAlbum(album);
            logger.debug("added/updated album {}", album);
            return "redirect:/albums";
        }
    }
}
