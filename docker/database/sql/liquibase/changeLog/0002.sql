CREATE TABLE if not exists public.projects
(
    id serial,
    name character varying,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.projects
    OWNER to postgres;