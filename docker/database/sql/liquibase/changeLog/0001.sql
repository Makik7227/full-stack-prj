CREATE TABLE if not exists public.employee
(
    id serial,
    "firstname" character varying(100),
    "lastname" character varying(100),
    "phonenumber" character varying(12),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.employee
    OWNER to postgres;