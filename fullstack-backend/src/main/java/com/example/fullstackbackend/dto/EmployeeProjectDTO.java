package com.example.fullstackbackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class EmployeeProjectDTO {

    @NotNull(message = "Employee ID cannot be null")
    private Integer employeeId;

    @NotNull(message = "Project ID cannot be null")
    private Integer projectId;


}


