package com.example.fullstackbackend.dao;

import com.example.fullstackbackend.model.EmployeeAssigned;
import com.example.fullstackbackend.model.EmployeeProject;
import com.example.fullstackbackend.model.Project;
import com.example.fullstackbackend.model.ProjectAssigned;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeProjectDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(EmployeeProject employeeProject, Integer id) {
        jdbcTemplate.update(
                "INSERT INTO public.employee_projects(employee_id, project_id,hours) VALUES (?, ?,0);", id, employeeProject.getProjectId());
    }

    public List<ProjectAssigned> findProjectByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query("select ep.id , p.id as idProject, p.name, ep.hours from public.projects p join public.employee_projects ep on p.id=ep.project_id where employee_id = ?", new Object[]{employeeId},
                new BeanPropertyRowMapper(ProjectAssigned.class));
    }

    public List<Project> findProjectNoAssignByEmployeeId(Integer employeeId) {
        return jdbcTemplate.query("select p.id,p.name from public.projects p where not exists  " +
                        "(select 1 from public.employee_projects ep where ep.employee_id = ? and ep.project_id = p.id)", new Object[]{employeeId},
                new BeanPropertyRowMapper(Project.class));
    }

    public void delete(Integer id, Integer idProject) {
        jdbcTemplate.update(
                "delete from public.employee_projects where employee_id=? and project_id=?", id, idProject);
    }

    public void deleteFromEmployeeProjects(Integer id) {
        jdbcTemplate.update(
                "delete from public.employee_projects where employee_id=?", id);
    }

    public void deleteFromProjectProjects(Integer id) {
        jdbcTemplate.update(
                "delete from public.employee_projects where project_id=?", id);
    }

    public List<EmployeeAssigned> findEmployeeByProjectId(Integer projectId) {
        return jdbcTemplate.query("select ep.id as idEmployee, p.id, p.firstname,p.lastname, ep.hours from public.employee p join public.employee_projects ep on p.id=ep.employee_id where project_id = ?", new Object[]{projectId},
                new BeanPropertyRowMapper(EmployeeAssigned.class));
    }

    public void saveHours(Integer empId, EmployeeAssigned employeeAssigned, Integer projId) {
        jdbcTemplate.update(
                "UPDATE public.employee_projects SET hours=hours+? WHERE employee_id=? and project_id=?;", employeeAssigned.getHours(), empId, projId);
    }


}
