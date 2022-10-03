create or replace function increase_version_trigger()
RETURNS trigger as
    $$
    BEGIN
        NEW.version=OLD.version+1;
        RETURN NEW;
    END;
    $$
language plpgsql;
