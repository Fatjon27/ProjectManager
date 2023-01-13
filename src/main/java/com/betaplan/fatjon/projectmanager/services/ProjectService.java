package com.betaplan.fatjon.projectmanager.services;

import com.betaplan.fatjon.projectmanager.models.Project;
import com.betaplan.fatjon.projectmanager.models.User;
import com.betaplan.fatjon.projectmanager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    public Project createProject(Project project){
        return projectRepository.save(project);
    }
    public Project updateProject(Project project){
        return projectRepository.save(project);
    }
    public void deleteById(Long id){
        projectRepository.deleteById(id);
    }
    public List<Project> findAll(){
        return projectRepository.findAll();
    }
    public List<Project> findAllProjectNotOnThisUser(User user){
        return projectRepository.findByUsersNotContains(user);
    }
    public List<Project> findAllProjectsThatThisUserHas(User user){
        return projectRepository.findAllByUsers(user);
    }
    public Project findById(Long id){
        Optional<Project> optionalProject = projectRepository.findById(id);
        if(optionalProject.isPresent()){
            return optionalProject.get();
        }
        else {
            return null;
        }
    }

}
