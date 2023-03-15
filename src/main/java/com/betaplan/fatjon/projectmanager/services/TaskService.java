package com.betaplan.fatjon.projectmanager.services;

import com.betaplan.fatjon.projectmanager.models.Project;
import com.betaplan.fatjon.projectmanager.models.Task;
import com.betaplan.fatjon.projectmanager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    public List<Task> findAll(){
        return taskRepository.findAll();
    }
    public Task createTask(Task task){
        return taskRepository.save(task);
    }
    public Task updateTask(Task task){
        return taskRepository.save(task);
    }
    public Task findById(Long id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(optionalTask.isPresent()){
            return optionalTask.get();
        }
        else {
            return null;
        }
    }
    public void deleteById(Long id){
        taskRepository.findById(id);
    }
    public List<Task> listById(Project project) {
        return taskRepository.findAllByProjectOrderById(project);
    }
}
