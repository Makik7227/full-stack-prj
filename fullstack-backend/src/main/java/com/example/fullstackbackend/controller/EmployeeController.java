package com.example.fullstackbackend.controller;


import com.example.fullstackbackend.dto.EmployeeDTO;
import com.example.fullstackbackend.model.Employee;
import com.example.fullstackbackend.model.EmployeeProject;
import com.example.fullstackbackend.model.Project;
import com.example.fullstackbackend.model.ProjectAssigned;
import com.example.fullstackbackend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employees")
@CrossOrigin(origins = "http://localhost:5173")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public List<EmployeeDTO> findAll() {
        return employeeService.findAll();
    }


    @DeleteMapping(value = "/{id}")
    public void deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployee(id);
    }

    @PostMapping(value = "/", consumes = "application/json")
    public void insert(@RequestBody EmployeeDTO employee) {
        employeeService.insertEmployee(employee);
    }


    @PutMapping("/{id}")
    public void update(@RequestBody EmployeeDTO employee, @PathVariable int id) {
        employeeService.updateEmployee(employee, id);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable int id) {
        return employeeService.findEmployeeById(id);
    }

    @GetMapping("/{id}/projects")
    public List<ProjectAssigned> findProjectByEmployee(@PathVariable int id) {
        return employeeService.findProjectsByEmployeeId(id);
    }

    @GetMapping("/{id}/unassignedProject")
    public List<Project> findProjectNotByEmployee(@PathVariable int id) {
        return employeeService.findUnassignedProjectsByEmployeeId(id);
    }

    @PostMapping(value = "/assign/{id}", consumes = "application/json")
    public void insert(@RequestBody EmployeeProject employeeProject, @PathVariable int id) {
        employeeService.assignProjectToEmployee(employeeProject, id);
    }


    @DeleteMapping(value = "/unassign/{id}/{idProject}")
    public void deleteEmployeeProject(@PathVariable Integer id, @PathVariable Integer idProject) {
        employeeService.unassignProjectFromEmployee(id, idProject);
    }


}
