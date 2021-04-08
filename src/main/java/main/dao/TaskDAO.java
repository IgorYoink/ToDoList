package main.dao;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskDAO {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAll(){
        Iterable<Task> taskIterable = taskRepository.findAll();
        List<Task> tasks = new ArrayList<>();

        taskIterable.forEach(tasks::add);
        return tasks;
    }

    public int add(Task task)
    {
        return taskRepository.save(task).getId();
    }

    public Optional<Task> get(int id)
    {
        return taskRepository.findById(id);
    }

    public Integer put(int id, String name, String isComplited)
    {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent())
        {
            return null;
        }

        taskOptional.get().setName(name);
        if(isComplited != null && isComplited.equals("true")) taskOptional.get().setComplited(true);
        else if (isComplited != null && isComplited.equals("0")) taskOptional.get().setComplited(false);
        taskRepository.save(taskOptional.get());

        return taskOptional.get().getId();
    }

    public boolean putAll(String name, String isComplited)
    {
        if (taskRepository.count() < 1)
        {
            return false;
        }

        Iterable<Task> taskIterable = taskRepository.findAll();

        taskIterable.forEach(task -> {
            task.setName(name);
            if (isComplited != null && isComplited.equals("true")) task.setComplited(true);
            else if (isComplited != null && isComplited.equals("0")) task.setComplited(false);
        });

        taskRepository.saveAll(taskIterable);
        return true;
    }

    public boolean delete(int id)
    {
        if (!taskRepository.findById(id).isPresent())
        {
            return false;
        }

        taskRepository.deleteById(id);
        return true;
    }

    public void deleteAll()
    {
        if (taskRepository.count() > 0)
        {
            taskRepository.deleteAll();
        }
    }

    public Integer patch(int id, String name, String isComplited)
    {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (!taskOptional.isPresent())
        {
            return null;
        }
        if (name != null) taskOptional.get().setName(name);
        if (isComplited != null && isComplited.equals("true"))
        {
            taskOptional.get().setComplited(true);
        }
        else if (isComplited != null && isComplited.equals("false"))
        {
            taskOptional.get().setComplited(false);
        }

        taskRepository.save(taskOptional.get());
        return taskOptional.get().getId();
    }

}
