package com.example.fullstackbackend.services;

import com.example.fullstackbackend.dao.EmployeeProjectDao;
import com.example.fullstackbackend.dao.ProjectDao;
import com.example.fullstackbackend.exceptions.NoProjectFoundByIdException;
import com.example.fullstackbackend.model.EmployeeAssigned;
import com.example.fullstackbackend.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectDao projectDao;

    @Mock
    private EmployeeProjectDao employeeProjectDao;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProjects() {
        // Mocking the behavior of the DAO
        when(projectDao.findAll()).thenReturn(Collections.singletonList(new Project()));

        // Performing the test
        List<Project> projects = projectService.getProjects();

        // Verifying interactions
        verify(projectDao, times(1)).findAll();

        // Verifying the result
        assertEquals(1, projects.size());
    }

    @Test
    void testDeleteProject() {
        // Mocking the behavior of the DAO
        doNothing().when(employeeProjectDao).deleteFromProjectProjects(1);
        doNothing().when(projectDao).delete(1);

        // Performing the test
        assertDoesNotThrow(() -> projectService.deleteProject(1));

        // Verifying interactions
        verify(employeeProjectDao, times(1)).deleteFromProjectProjects(1);
        verify(projectDao, times(1)).delete(1);
    }

    @Test
    void testInsertProject() {
        // Mocking the behavior of the DAO
        doNothing().when(projectDao).insert(any());

        // Performing the test
        assertDoesNotThrow(() -> projectService.insertProject(new Project()));

        // Verifying interactions
        verify(projectDao, times(1)).insert(any());
    }

    @Test
    void testFindProjectById() {
        // Mocking the behavior of the DAO
        when(projectDao.findById(1)).thenReturn(new Project());

        // Performing the test
        assertDoesNotThrow(() -> projectService.findProjectById(1));

        // Verifying interactions
        verify(projectDao, times(1)).findById(1);
    }

    @Test
    void testFindProjectByIdNotFound() {
        // Mocking the behavior of the DAO to throw EmptyResultDataAccessException
        when(projectDao.findById(1)).thenThrow(new EmptyResultDataAccessException(1));

        // Performing the test and expecting an exception
        NoProjectFoundByIdException exception = assertThrows(NoProjectFoundByIdException.class,
                () -> projectService.findProjectById(1));

        // Verifying interactions
        verify(projectDao, times(1)).findById(1);

        // Verifying the exception message
        assertEquals("Project with ID 1 not found", exception.getMessage());
    }

    @Test
    void testUpdateProject() {
        // Mocking the behavior of the DAO
        doNothing().when(projectDao).update(any(), eq(1));

        // Performing the test
        assertDoesNotThrow(() -> projectService.updateProject(new Project(), 1));

        // Verifying interactions
        verify(projectDao, times(1)).update(any(), eq(1));
    }

    @Test
    void testFindEmployeeByProjectId() {
        // Mocking the behavior of the DAO
        when(employeeProjectDao.findEmployeeByProjectId(1)).thenReturn(Collections.singletonList(new EmployeeAssigned()));

        // Performing the test
        List<EmployeeAssigned> employeeAssignedList = projectService.findEmployeeByProjectId(1);

        // Verifying interactions
        verify(employeeProjectDao, times(1)).findEmployeeByProjectId(1);

        // Verifying the result
        assertEquals(1, employeeAssignedList.size());
    }

    @Test
    void testSaveHours() {
        // Mocking the behavior of the DAO
        doNothing().when(employeeProjectDao).saveHours(anyInt(), any(EmployeeAssigned.class), anyInt());

        // Creating an EmployeeAssigned object with non-null hours
        EmployeeAssigned employeeAssigned = new EmployeeAssigned();
        employeeAssigned.setHours(10); // Set a non-null value for hours

        // Performing the test
        assertDoesNotThrow(() -> projectService.saveHours(1, employeeAssigned, 2));

        // Verifying interactions
        verify(employeeProjectDao, times(1)).saveHours(anyInt(), any(EmployeeAssigned.class), anyInt());
    }
}