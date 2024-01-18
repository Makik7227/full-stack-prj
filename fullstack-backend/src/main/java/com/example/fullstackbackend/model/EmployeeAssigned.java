package com.example.fullstackbackend.model;


import lombok.Data;

@Data
public class EmployeeAssigned {

    private Integer id;

    private Integer idProject;

    private Integer idEmployee;

    private String firstName;

    private String lastName;

    private Integer hours;


}
