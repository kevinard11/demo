DROP TRIGGER IF EXISTS  call_increase_version_trigger ON demo;
CREATE TRIGGER call_increase_version_trigger
    before update
    on demo
    for each row
    EXECUTE PROCEDURE increase_version_trigger();