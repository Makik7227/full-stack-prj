package com.example.fullstackbackend.model;

import lombok.Data;

@Data
public class EmployeeProject {

    private Integer id;

    private Integer projectId;

    private Integer employeeId;

    private Integer hours;
}
