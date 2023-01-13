package com.betaplan.fatjon.projectmanager.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First Name must be provided.")
    @Size(min=3,message = "First Name must be at least 3 characters.")
    private String firstName;
    @NotBlank(message = "Last Name must be provided.")
    @Size(min=3,message = "Last Name must be at least 3 characters.")
    private String lastName;
    @Email
    @NotBlank(message = "Email must be provided.")
    @Size(min=5,message = "Email must be at least 5 characters.")
    private String email;
    @NotBlank(message = "Password must be provided.")
    @Size(min=8,message = "Password must be at least 8 characters.")
    private String password;
    @Transient
    @NotBlank(message = "Confirm must be provided.")
    @Size(min=8,message = "Confirm must be at least 8 characters.")
    private String confirm;
    @Column(updatable = false)
    private Date createdAt;
    private Date updatedAt;
    @OneToMany(mappedBy = "teamLeader",fetch = FetchType.LAZY)
    private List<Project> leadingProjects;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_projects",
               joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> participatingProjects;
    public User(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }
    @PreUpdate
    protected void  onUpdate(){
        this.updatedAt= new Date();
    }

    public List<Project> getLeadingProjects() {
        return leadingProjects;
    }

    public void setLeadingProjects(List<Project> leadingProjects) {
        this.leadingProjects = leadingProjects;
    }

    public List<Project> getParticipatingProjects() {
        return participatingProjects;
    }

    public void setParticipatingProjects(List<Project> participatingProjects) {
        this.participatingProjects = participatingProjects;
    }
}
