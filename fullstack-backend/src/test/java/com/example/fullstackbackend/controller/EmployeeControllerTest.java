package com.example.fullstackbackend.controller;

import com.example.fullstackbackend.dto.EmployeeDTO;
import com.example.fullstackbackend.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

public class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        List<EmployeeDTO> employees = Arrays.asList(new EmployeeDTO(), new EmployeeDTO());

        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(MockMvcRequestBuilders.get("/employees/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testInsertEmployee() throws Exception {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        String json = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"phoneNumber\":\"1234567890\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/employees/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
