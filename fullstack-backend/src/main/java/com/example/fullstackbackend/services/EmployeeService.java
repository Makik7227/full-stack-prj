package com.example.fullstackbackend.services;

import com.example.fullstackbackend.dao.EmployeeDao;
import com.example.fullstackbackend.dao.EmployeeProjectDao;
import com.example.fullstackbackend.dto.EmployeeDTO;
import com.example.fullstackbackend.exceptions.NoProjectFoundByIdException;
import com.example.fullstackbackend.mapper.EmployeeMapper;
import com.example.fullstackbackend.model.Employee;
import com.example.fullstackbackend.model.EmployeeProject;
import com.example.fullstackbackend.model.Project;
import com.example.fullstackbackend.model.ProjectAssigned;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmployeeProjectDao employeeProjectDao;

    @Autowired
    EmployeeMapper employeeMapper;

    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeDao.findAll();
        return employees.stream()
                .map(employeeMapper::mapToEmployeeDTO)
                .collect(Collectors.toList());
    }

    public void deleteEmployee(int id) {
        employeeProjectDao.deleteFromEmployeeProjects(id);
        employeeDao.delete(id);
    }

    public void insertEmployee(@Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.mapToEmployee(employeeDTO);
        employeeDao.insert(employee);
    }

    public void updateEmployee(@Valid EmployeeDTO employeeDTO, int id) {
        Employee existingEmployee = employeeDao.findById(id);
        if (existingEmployee != null) {
            Employee updatedEmployee = employeeMapper.mapToEmployee(employeeDTO);
            employeeDao.update(updatedEmployee, id);
        }
    }

    public Employee findEmployeeById(int id) {
        try {
            return employeeDao.findById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new NoProjectFoundByIdException("Employee with ID " + id + " not found");
        }
    }

    public List<ProjectAssigned> findProjectsByEmployeeId(int id) {
        return employeeProjectDao.findProjectByEmployeeId(id);
    }

    public List<Project> findUnassignedProjectsByEmployeeId(int id) {
        return employeeProjectDao.findProjectNoAssignByEmployeeId(id);
    }

    public void assignProjectToEmployee(EmployeeProject employeeProject, int id) {
        employeeProjectDao.save(employeeProject, id);
    }

    public void unassignProjectFromEmployee(int employeeId, int projectId) {
        employeeProjectDao.delete(employeeId, projectId);
    }

}
