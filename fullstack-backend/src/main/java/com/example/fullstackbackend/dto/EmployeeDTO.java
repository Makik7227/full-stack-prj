package com.example.fullstackbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeDTO {

    @NotNull
    private Integer id;

    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;
}
