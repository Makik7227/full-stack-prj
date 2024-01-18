package com.example.fullstackbackend.mapper;

import com.example.fullstackbackend.dto.EmployeeDTO;
import com.example.fullstackbackend.dto.EmployeeProjectDTO;
import com.example.fullstackbackend.model.Employee;
import com.example.fullstackbackend.model.EmployeeProject;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee mapToEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO mapToEmployeeDTO(Employee employee);

    EmployeeProject mapToEmployeeProject(EmployeeProjectDTO employeeProjectDTO);

    EmployeeProjectDTO mapToEmployeeProjectDTO(EmployeeProject employeeProject);
}
