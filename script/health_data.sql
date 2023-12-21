CREATE TABLE IF NOT EXISTS public.health_data
(
    id integer NOT NULL DEFAULT nextval('health_data_id_seq'::regclass),
    user_id integer NOT NULL,
    weight numeric,
    height numeric,
    steps numeric,
    heart_rate numeric,
    date date,
    CONSTRAINT health_data_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.health_data
    OWNER to postgres;
