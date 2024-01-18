package com.example.fullstackbackend.dao;

import com.example.fullstackbackend.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Employee> findAll() {
        return jdbcTemplate.query("select * from public.employee", new BeanPropertyRowMapper<>(Employee.class));
    }

    public void insert(Employee employee) {
        jdbcTemplate.update(
                "INSERT INTO public.employee(\"firstname\", \"lastname\", \"phonenumber\") VALUES (?,?,?);", employee.getFirstName(),
                employee.getLastName(),
                employee.getPhoneNumber());
    }

    public void delete(Integer id) {
        jdbcTemplate.update(
                "delete from public.employee where id = ?", id);
    }

    public void update(Employee employee, Integer id) {
        jdbcTemplate.update(
                "update public.employee SET \"firstname\"=?, \"lastname\"=?,\"phonenumber\"=? WHERE id=?", employee.getFirstName(), employee.getLastName(), employee.getPhoneNumber(), id);

    }

    public Employee findById(int id) {
        return (Employee) jdbcTemplate.queryForObject("select * from public.employee where id = ?", new Object[]{id},
                new BeanPropertyRowMapper(Employee.class));
    }


}
