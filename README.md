# OOPDB
для успешного запуска программы необходимо создать базу данных postgresql.
скрипт создания базы данных:
-- Database: oopdb
CREATE DATABASE oopdb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
   
скрипт создания таблиц:
CREATE TABLE IF NOT EXISTS public.company
(
    name text COLLATE pg_catalog."default" NOT NULL,
    income integer,
    CONSTRAINT company_pkey PRIMARY KEY (name)
)

CREATE TABLE IF NOT EXISTS public.manager
(
    company_name text COLLATE pg_catalog."default" NOT NULL,
    personal_income integer,
    mounthsalary integer,
    id integer NOT NULL DEFAULT nextval('manager_id_seq'::regclass),
    CONSTRAINT id_pk PRIMARY KEY (id),
    CONSTRAINT manager_company_name_fkey FOREIGN KEY (company_name)
        REFERENCES public.company (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS public.operator
(
    monthsalary integer NOT NULL,
    company_name text COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 0 MINVALUE 0 MAXVALUE 2147483647 CACHE 1 ),
    CONSTRAINT operator_pkey PRIMARY KEY (id),
    CONSTRAINT operator_company_name_fkey FOREIGN KEY (company_name)
        REFERENCES public.company (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

CREATE TABLE IF NOT EXISTS public.topmanager
(
    mounthsalary integer,
    company_name text COLLATE pg_catalog."default" NOT NULL,
    id integer NOT NULL DEFAULT nextval('topmanager_id_seq'::regclass),
    CONSTRAINT id_top_pk PRIMARY KEY (id),
    CONSTRAINT topmanager_company_name_fkey FOREIGN KEY (company_name)
        REFERENCES public.company (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
