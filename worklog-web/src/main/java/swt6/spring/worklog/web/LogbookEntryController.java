package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.LogbookEntry;
import swt6.spring.worklog.logic.WorkLogFacade;
import swt6.util.LocalDateTimeEditor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class LogbookEntryController {

    private final Logger logger = LoggerFactory.getLogger(LogbookEntryController.class);

    @Autowired
    private WorkLogFacade workLog;

    @RequestMapping("/employees/{employeeId}/entries")
    public String list(@PathVariable("employeeId") long employeeId, Model model) {
        Employee empl =  workLog.findEmployeeById(employeeId);

        logger.debug("details view for employee {}", empl);

        List<LogbookEntry> entries = new ArrayList<>(empl.getLogbookEntries());
        Collections.sort(entries, (e1, e2) -> e1.getStartTime().compareTo(e2.getStartTime()) );

        model.addAttribute("employee", empl);
        model.addAttribute("logbookEntries", entries);

        return "entryList";
    }

    @RequestMapping(value="/employees/{employeeId}/entries/new", method = RequestMethod.GET)
    public String initNew(@PathVariable("employeeId") Long employeeId, Model model) {

        LocalDateTime now = LocalDateTime.now();
        LogbookEntry entry = new LogbookEntry("", now.minusHours(1), now);

        Employee empl = this.workLog.findEmployeeById(employeeId);
        entry.setEmployee(empl);
        model.addAttribute("entry", entry);

        logger.debug("entry template [}", entry);

        return "entry";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LocalDateTime.class,
                new LocalDateTimeEditor(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    @RequestMapping(value="/employees/{employeeId}/entries/new", method = RequestMethod.POST)
    public String processNew(@PathVariable("employeeId") Long employeeId,
                             @ModelAttribute("entry") LogbookEntry entry,
                             BindingResult result, Model model) {

        return internalProcessUpdate(employeeId, entry, result);
    }

    private String internalProcessUpdate(Long employeeId, LogbookEntry entry, BindingResult result) {
        if (result.hasErrors()) {
            return "entry";
        }
        else {
            entry.setEmployee(workLog.findEmployeeById(employeeId));
            entry = workLog.syncLogbookEntry(entry);

            logger.debug("added/updated entry {}", entry);
            return "redirect:/employees/{employeeId}/entries";
        }
    }

    @RequestMapping(value="/employees/{employeeId}/entries/{entryId}/update", method = RequestMethod.GET)
    public String initUpdate(@PathVariable("employeeId") Long employeeId,
                             @PathVariable("entryId") Long entryId,
                             Model model) {

        LogbookEntry entry = workLog.findLogbookEntryById(entryId);
        model.addAttribute("entry", entry);

        logger.debug("entry template [}", entry);

        return "entry";
    }

    @RequestMapping(value="/employees/{employeeId}/entries/{entryId}/update", method = RequestMethod.POST)
    public String processUpdate(@PathVariable("employeeId") Long employeeId,
                                @ModelAttribute("entry") LogbookEntry entry,
                                BindingResult result) {
        return internalProcessUpdate(employeeId, entry, result);

    }

}
