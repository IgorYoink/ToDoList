package main;

import main.dao.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import main.model.Task;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskDAO taskDAO;

    @Autowired
    public TaskController(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @GetMapping()
    public List<Task> list()
    {
        return taskDAO.getAll();
    }

    @PostMapping()
    public int add(Task task)
    {
        return taskDAO.add(task);
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable int id)
    {
        if (!taskDAO.get(id).isPresent())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(taskDAO.get(id).get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable int id, String name, String isComplited)
    {
        if (taskDAO.put(id, name,isComplited) == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return new ResponseEntity(taskDAO.put(id, name,isComplited), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity putAll(String name, String isComplited)
    {
        if (!taskDAO.putAll(name, isComplited))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id)
    {
        if (!taskDAO.delete(id))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity( HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity deleteAll()
    {
        taskDAO.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patch(@PathVariable int id, String name, String isComplited)
    {
        if (taskDAO.patch(id, name, isComplited) == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(taskDAO.patch(id, name, isComplited), HttpStatus.OK);
    }


}
