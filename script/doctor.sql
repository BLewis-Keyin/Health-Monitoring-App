CREATE TABLE IF NOT EXISTS public.doctors
(
    id integer NOT NULL DEFAULT nextval('doctors_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default",
    license_number character varying COLLATE pg_catalog."default",
    specialization character varying COLLATE pg_catalog."default",
    CONSTRAINT doctors_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.doctors
    OWNER to postgres;