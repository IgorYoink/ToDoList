package main;

import main.dao.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    private TaskDAO taskDAO;

    @Autowired
    public DefaultController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @RequestMapping("/")
    public String index(Model model)
    {
        model.addAttribute("tasks", taskDAO.getAll());
        model.addAttribute("tasksCount", taskDAO.getAll().size());

        return "index";
    }
}
