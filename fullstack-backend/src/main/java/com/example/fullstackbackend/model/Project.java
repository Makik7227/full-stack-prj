package com.example.fullstackbackend.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Project {

    @NotBlank
    String name;

    @NotNull
    private Integer id;

}
