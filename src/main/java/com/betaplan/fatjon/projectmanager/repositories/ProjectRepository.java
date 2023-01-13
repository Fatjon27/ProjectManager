package com.betaplan.fatjon.projectmanager.repositories;

import com.betaplan.fatjon.projectmanager.models.Project;
import com.betaplan.fatjon.projectmanager.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends CrudRepository<Project,Long> {
    List<Project> findAll();
    List<Project> findByUsersNotContains(User user);
    List<Project> findAllByUsers(User user);
}
