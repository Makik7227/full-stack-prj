package com.example.fullstackbackend.controller;


import com.example.fullstackbackend.model.EmployeeAssigned;
import com.example.fullstackbackend.model.Project;
import com.example.fullstackbackend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projects")
@CrossOrigin(origins = "http://localhost:5173")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "")
    public List<Project> getProjects() {
        return projectService.getProjects();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public void insert(@RequestBody Project project) {
        projectService.insertProject(project);
    }

    @GetMapping("/{id}")
    public Project findById(@PathVariable int id) {
        return projectService.findProjectById(id);
    }

    @PutMapping("/{id}")
    public void update(@RequestBody Project project, @PathVariable int id) {
        projectService.updateProject(project, id);
    }

    @GetMapping("/{id}/byId")
    public List<EmployeeAssigned> findEmployeeByProjectId(@PathVariable int id) {
        return projectService.findEmployeeByProjectId(id);
    }

    @PutMapping(value = "/hours/{empId}/{projId}", consumes = "application/json")
    public void insert(@PathVariable int empId, @RequestBody EmployeeAssigned hours, @PathVariable int projId) {
        projectService.saveHours(empId, hours, projId);
    }


}


