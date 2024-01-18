CREATE TABLE public.employee_projects
(
    id serial,
    project_id integer,
    employee_id integer,
    hours integer,
    PRIMARY KEY (id),
    CONSTRAINT fk_employee FOREIGN KEY (employee_id)
        REFERENCES public.employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_project FOREIGN KEY (project_id)
        REFERENCES public.projects (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS public.employee_projects
    OWNER to postgres;

create view project_view as select p.id as project_id, ep.employee_id, ep.id, p.name, ep.hours from public.projects p left join public.employee_projects ep
                                                                                                                                on p.id = ep.project_id;