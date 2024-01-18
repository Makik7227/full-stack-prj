package com.example.fullstackbackend.controller;

import com.example.fullstackbackend.model.EmployeeAssigned;
import com.example.fullstackbackend.model.Project;
import com.example.fullstackbackend.services.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProjectControllerTest {

    @Mock
    ProjectService projectService;

    @InjectMocks
    ProjectController projectController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    void testGetProjects() throws Exception {
        // Mocking the service method
        when(projectService.getProjects()).thenReturn(Arrays.asList(new Project(), new Project()));

        // Building the request
        RequestBuilder request = MockMvcRequestBuilders
                .get("/projects")
                .accept(MediaType.APPLICATION_JSON);

        // Verifying the expected result
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Verifying the number of projects returned
        List<Project> projects = Arrays.asList(new ObjectMapper().readValue(result.getResponse().getContentAsString(), Project[].class));
        assertEquals(2, projects.size());
    }

    @Test
    void testDeleteProject() throws Exception {
        // Building the request
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/projects/1")
                .accept(MediaType.APPLICATION_JSON);

        // Performing the request and verifying the status
        mockMvc.perform(request)
                .andExpect(status().isOk());

        // Verifying that the service method was called
        verify(projectService, times(1)).deleteProject(1);
    }

    @Test
    void testInsertProject() throws Exception {
        // Mocking the service method
        doNothing().when(projectService).insertProject(any(Project.class));

        // Building the request with a sample project JSON payload
        String projectJson = "{ \"name\": \"Sample Project\", \"description\": \"Sample Description\" }";
        RequestBuilder request = MockMvcRequestBuilders
                .post("/projects/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectJson)
                .accept(MediaType.APPLICATION_JSON);

        // Performing the request and verifying the status
        mockMvc.perform(request)
                .andExpect(status().isOk());

        // Verifying that the service method was called with the correct argument
        verify(projectService, times(1)).insertProject(any(Project.class));
    }

    @Test
    void testUpdate() throws Exception {
        // Mocking the service method
        doNothing().when(projectService).updateProject(any(Project.class), eq(1));

        // Building the request with a sample project JSON payload
        String projectJson = "{ \"name\": \"Updated Project\", \"description\": \"Updated Description\" }";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/projects/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(projectJson)
                .accept(MediaType.APPLICATION_JSON);

        // Performing the request and verifying the status
        mockMvc.perform(request)
                .andExpect(status().isOk());

        // Verifying that the service method was called with the correct arguments
        verify(projectService, times(1)).updateProject(any(Project.class), eq(1));
    }

    @Test
    void testFindEmployeeByProjectId() throws Exception {
        // Mocking the service method
        when(projectService.findEmployeeByProjectId(1)).thenReturn(Arrays.asList(new EmployeeAssigned(), new EmployeeAssigned()));

        // Building the request
        RequestBuilder request = MockMvcRequestBuilders
                .get("/projects/1/byId")
                .accept(MediaType.APPLICATION_JSON);

        // Verifying the expected result
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        // Verifying that the service method was called with the correct argument
        verify(projectService, times(1)).findEmployeeByProjectId(1);

        // Verifying the number of employeeAssigned objects returned
        List<EmployeeAssigned> employeeAssignedList = Arrays.asList(new ObjectMapper().readValue(result.getResponse().getContentAsString(), EmployeeAssigned[].class));
        assertEquals(2, employeeAssignedList.size());
    }

    @Test
    void testInsertHours() throws Exception {
        // Mocking the service method
        doNothing().when(projectService).saveHours(eq(1), any(EmployeeAssigned.class), eq(2));

        // Building the request with a sample EmployeeAssigned JSON payload
        String employeeAssignedJson = "{ \"employeeId\": 1, \"hoursWorked\": 40 }";
        RequestBuilder request = MockMvcRequestBuilders
                .put("/projects/hours/1/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeAssignedJson)
                .accept(MediaType.APPLICATION_JSON);

        // Performing the request and verifying the status
        mockMvc.perform(request)
                .andExpect(status().isOk());

        // Verifying that the service method was called with the correct arguments
        verify(projectService, times(1)).saveHours(eq(1), any(EmployeeAssigned.class), eq(2));
    }
}
