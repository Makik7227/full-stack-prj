package com.example.fullstackbackend.dao;

import com.example.fullstackbackend.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ProjectDao {


    @Autowired

    private JdbcTemplate jdbcTemplate;

    public List<Project> findAll() {
        return jdbcTemplate.query("select * from public.projects", new BeanPropertyRowMapper<>(Project.class));
    }

    public void delete(Integer id) {
        jdbcTemplate.update("delete from public.projects where id = ?", id);
    }

    public void insert(Project project) {
        jdbcTemplate.update("INSERT INTO public.projects(name) VALUES (?);", project.getName());
    }

    public void update(Project project, Integer Id) {
        jdbcTemplate.update("update public.projects SET name=? WHERE id=?", project.getName(), Id);

    }

    public Project findById(int id) {
        return (Project) jdbcTemplate.queryForObject("select * from public.projects where id = ?", new Object[]{id}, new BeanPropertyRowMapper(Project.class));
    }


}
