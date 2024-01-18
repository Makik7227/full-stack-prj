package com.example.fullstackbackend.services;

import com.example.fullstackbackend.dao.EmployeeDao;
import com.example.fullstackbackend.dao.EmployeeProjectDao;
import com.example.fullstackbackend.dto.EmployeeDTO;
import com.example.fullstackbackend.exceptions.NoProjectFoundByIdException;
import com.example.fullstackbackend.mapper.EmployeeMapper;
import com.example.fullstackbackend.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @Mock
    private EmployeeProjectDao employeeProjectDao;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private LocalValidatorFactoryBean validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();
    }

    @Test
    void testFindAll() {
        // Mocking the behavior of the DAO and Mapper
        when(employeeDao.findAll()).thenReturn(Collections.singletonList(new Employee()));
        when(employeeMapper.mapToEmployeeDTO(any())).thenReturn(new EmployeeDTO());

        // Performing the test
        assertEquals(1, employeeService.findAll().size());

        // Verifying interactions
        verify(employeeDao, times(1)).findAll();
        verify(employeeMapper, times(1)).mapToEmployeeDTO(any());
    }

    @Test
    void testDeleteEmployee() {
        // Performing the test
        assertDoesNotThrow(() -> employeeService.deleteEmployee(1));

        // Verifying interactions
        verify(employeeProjectDao, times(1)).deleteFromEmployeeProjects(1);
        verify(employeeDao, times(1)).delete(1);
    }

    @Test
    void testInsertEmployee() {
        // Mocking the behavior of the Mapper
        when(employeeMapper.mapToEmployee(any())).thenReturn(new Employee());

        // Performing the test
        assertDoesNotThrow(() -> employeeService.insertEmployee(new EmployeeDTO()));

        // Verifying interactions
        verify(employeeMapper, times(1)).mapToEmployee(any());
        verify(employeeDao, times(1)).insert(any());
    }

    @Test
    void testUpdateEmployee() {
        // Mocking the behavior of the DAO and Mapper
        when(employeeDao.findById(1)).thenReturn(new Employee());
        when(employeeMapper.mapToEmployee(any())).thenReturn(new Employee());

        // Performing the test
        assertDoesNotThrow(() -> employeeService.updateEmployee(new EmployeeDTO(), 1));

        // Verifying interactions
        verify(employeeDao, times(1)).findById(1);
        verify(employeeDao, times(1)).update(any(), eq(1));
        verify(employeeMapper, times(1)).mapToEmployee(any());
    }

    @Test
    void testFindEmployeeById() {
        // Mocking the behavior of the DAO
        when(employeeDao.findById(1)).thenReturn(new Employee());

        // Performing the test
        assertDoesNotThrow(() -> employeeService.findEmployeeById(1));

        // Verifying interactions
        verify(employeeDao, times(1)).findById(1);
    }

    @Test
    void testFindEmployeeByIdNotFound() {
        // Mocking the behavior of the DAO to throw EmptyResultDataAccessException
        when(employeeDao.findById(1)).thenThrow(new EmptyResultDataAccessException(1));

        // Performing the test and expecting an exception
        NoProjectFoundByIdException exception = assertThrows(NoProjectFoundByIdException.class,
                () -> employeeService.findEmployeeById(1));

        // Verifying interactions
        verify(employeeDao, times(1)).findById(1);

        // Verifying the exception message
        assertEquals("Employee with ID 1 not found", exception.getMessage());
    }


}
