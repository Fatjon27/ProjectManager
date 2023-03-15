package com.betaplan.fatjon.projectmanager.controllers;

import com.betaplan.fatjon.projectmanager.models.LoginUser;
import com.betaplan.fatjon.projectmanager.models.Project;
import com.betaplan.fatjon.projectmanager.models.Task;
import com.betaplan.fatjon.projectmanager.models.User;
import com.betaplan.fatjon.projectmanager.services.ProjectService;
import com.betaplan.fatjon.projectmanager.services.TaskService;
import com.betaplan.fatjon.projectmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public String index(Model model, @ModelAttribute("newUser") User user, @ModelAttribute("newLogin") LoginUser newLogin) {
        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index";
    }

    @PostMapping("/register")
    public String register(Model model, HttpSession session, @Valid @ModelAttribute("newUser") User newUser, BindingResult result) {
        userService.register(newUser, result);
        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index";
        } else {
            session.setAttribute("loggedInUserId", newUser.getId());
            return "redirect:/dashboard";
        }
    }

    @PostMapping("/login")
    public String login(Model model, HttpSession session, @Valid @ModelAttribute("newLogin") LoginUser newLogin, BindingResult result) {
        User user = userService.login(newLogin, result);
        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index";
        } else {
            session.setAttribute("loggedInUserId", user.getId());
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/";
        } else {
            User user = userService.findById(loggedInUserId);
            model.addAttribute("user", user);
//            ArrayList<Project> notYourProjects = new ArrayList<Project>();
//            for(Project project: projectService.findAll()){
//                if(!project.getTeamLeader().equals(user)){
//                    notYourProjects.add(project);
//                }
            model.addAttribute("notYourProjects", projectService.findAllProjectNotOnThisUser(user));
//            ArrayList<Project> projects = new ArrayList<Project>();
//            for (Project project : projectService.findAll()) {
//                if (project.getTeamLeader().equals(user) || project.getUsers().contains(user)) {
//                    projects.add(project);
//                }
//            }
            model.addAttribute("projects", user.getParticipatingProjects());
            return "dashboard";
        }
    }

    @GetMapping("/projects/new")
    public String createP(HttpSession session, Model model, @ModelAttribute("project") Project project) {
        Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
        if (loggedInUserId == null) {
            return "redirect:/dashboard";
        } else {
            User user = userService.findById(loggedInUserId);
            model.addAttribute("user", user);
            return "createP";
        }
    }
    @PostMapping("/projects/new")
    public String createProject(HttpSession session,Model model,@Valid @ModelAttribute("project") Project project,BindingResult result){
        if(result.hasErrors()){
            Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
            if (loggedInUserId == null) {
                return "redirect:/dashboard";
            } else {
                User user = userService.findById(loggedInUserId);
                model.addAttribute("user", user);
                return "createP";
            }
        }
        else {
            Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
            User user = userService.findById(loggedInUserId);
            project.setTeamLeader(user);
            projectService.createProject(project);
            user.getParticipatingProjects().add(project);
            userService.updateUser(user);
            return "redirect:/dashboard";
        }
    }
    @GetMapping("/projects/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id){
        Project project = projectService.findById(id);
        model.addAttribute("project",project);
        return "editP";
    }
    @PutMapping("/projects/edit/{id}")
    public String update(Model model,HttpSession session,@ModelAttribute("project") Project project,BindingResult result){
        if(result.hasErrors()){
            return "editP";
        }
        else {
            Long loggedInUserId = (Long) session.getAttribute("loggedInUserId");
            User user = userService.findById(loggedInUserId);
            project.setTeamLeader(user);
            projectService.updateProject(project);
            user.getParticipatingProjects().add(project);
            userService.updateUser(user);
            return "redirect:/dashboard";
        }
    }
    @GetMapping("/join/{id}")
    public String join(@PathVariable("id") Long id,HttpSession session){
        Project project = projectService.findById(id);
        User user = userService.findById((Long) session.getAttribute("loggedInUserId"));
        user.getParticipatingProjects().add(project);
        userService.updateUser(user);
        return "redirect:/dashboard";
    }
    @GetMapping("leave/{id}")
    public String leave(@PathVariable("id") Long id,HttpSession session){
        Project project = projectService.findById(id);
        User user = userService.findById((Long) session.getAttribute("loggedInUserId"));
        user.getParticipatingProjects().remove(project);
        userService.updateUser(user);
        return "redirect:/dashboard";
    }

    @GetMapping("/projects/{id}")
    public String projectDetails(@PathVariable("id") Long id,Model model){
        Project project = projectService.findById(id);
        model.addAttribute("project",project);
//
//        List<Task> task1= taskService.listById(project);
//        model.addAttribute("list",task1);
        return "projectDetails";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteProject(@PathVariable("id") Long id,HttpSession session){
        Project project = projectService.findById(id);
        User user = userService.findById((Long) session.getAttribute("loggedInUserId"));
        if(project.getTeamLeader().equals(user)) {
            projectService.deleteById(id);
            return "redirect:/dashboard";
        }
        else {
            return "redirect:/dashboard";
        }
    }
    @GetMapping("/projects/{id}/tasks")
    public String tasks(@PathVariable("id") Long id, Model model, HttpSession session, @ModelAttribute("task")Task task){
        Project project = projectService.findById(id);
        User user = userService.findById((Long) session.getAttribute("loggedInUserId"));
        model.addAttribute("project",project);
        model.addAttribute("user",user);
        return "task";
    }
    @PostMapping("/projects/{id}/tasks")
    public String createTask(@PathVariable("id") Long id, Model model, HttpSession session,@Valid @ModelAttribute("task")Task task,BindingResult result){
        Project project = projectService.findById(id);
        User user = userService.findById((Long) session.getAttribute("loggedInUserId"));
        model.addAttribute("project",project);
        model.addAttribute("user",user);
        if(session.getAttribute("loggedInUserId")==null){
            return "redirect:/logout";
        }
        else {
            if(result.hasErrors()){
                return "task" ;
            }
            else {
                task.setUser(user);
                task.setProject(project);
                taskService.createTask(task);
                return "redirect:/projects/"+id+"/tasks";
            }
        }
    }
}