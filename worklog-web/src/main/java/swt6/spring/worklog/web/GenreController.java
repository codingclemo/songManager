package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import swt6.spring.worklog.domain.Genre;
import swt6.spring.worklog.logic.SongManagerFacade;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class GenreController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private SongManagerFacade songManager;

    @RequestMapping("/genres")
    public String list(Model model) {
        List<Genre> genres = songManager.findAllGenres();
        Collections.sort(genres, Comparator.comparing(Genre::getName));
        model.addAttribute("genres", genres);
        logger.debug("genres: " + genres);
        return "genreList";
    }
}
