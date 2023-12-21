CREATE TABLE IF NOT EXISTS public.recommendations
(
    user_id integer NOT NULL DEFAULT nextval('recommendations_user_id_seq'::regclass),
    recommendation character varying COLLATE pg_catalog."default"
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.recommendations
    OWNER to postgres;