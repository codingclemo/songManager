package swt6.spring.worklog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.logic.WorkLogFacade;

import java.util.List;

@Controller
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private WorkLogFacade workLogFacade;

    @RequestMapping("/employees")
    public String list(Model model) {
        List<Employee> employees = this.workLogFacade.findAllEmployees();
        model.addAttribute("employees", employees);
        logger.debug("employees: " + employees);
        return "employeeList";
    }

}
