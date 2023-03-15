package com.betaplan.fatjon.projectmanager.repositories;

import com.betaplan.fatjon.projectmanager.models.Project;
import com.betaplan.fatjon.projectmanager.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task,Long> {
    List<Task> findAll();
    List<Task> findAllByProjectOrderById(Project project);
}
