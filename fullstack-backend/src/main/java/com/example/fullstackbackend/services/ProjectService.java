package com.example.fullstackbackend.services;

import com.example.fullstackbackend.dao.EmployeeProjectDao;
import com.example.fullstackbackend.dao.ProjectDao;
import com.example.fullstackbackend.exceptions.NoProjectFoundByIdException;
import com.example.fullstackbackend.model.EmployeeAssigned;
import com.example.fullstackbackend.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectDao projectDao;
    private final EmployeeProjectDao employeeProjectDao;

    @Autowired
    public ProjectService(ProjectDao projectDao, EmployeeProjectDao employeeProjectDao) {
        this.projectDao = projectDao;
        this.employeeProjectDao = employeeProjectDao;
    }

    @Transactional(readOnly = true)
    public List<Project> getProjects() {
        return projectDao.findAll();
    }

    @Transactional
    public void deleteProject(int id) {
        employeeProjectDao.deleteFromProjectProjects(id);
        projectDao.delete(id);
    }

    @Transactional
    public void insertProject(Project project) {
        projectDao.insert(project);
    }

    @Transactional(readOnly = true)
    public Project findProjectById(int id) {
        try {
            return projectDao.findById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoProjectFoundByIdException("Project with ID " + id + " not found");
        }
    }

    @Transactional
    public void updateProject(Project project, int id) {
        projectDao.update(project, id);
    }

    @Transactional(readOnly = true)
    public List<EmployeeAssigned> findEmployeeByProjectId(int id) {
        return employeeProjectDao.findEmployeeByProjectId(id);
    }

    @Transactional
    public void saveHours(int empId, EmployeeAssigned hours, int projId) {
        int hoursValue = (hours != null) ? hours.getHours() : 0;
        EmployeeAssigned updatedHours = new EmployeeAssigned();
        updatedHours.setHours(hoursValue);
        employeeProjectDao.saveHours(empId, updatedHours, projId);
    }
}
