

### Create the schema
```
CREATE USER champ_theatre IDENTIFIED BY champ_theatre;
GRANT CONNECT TO champ_theatre;
GRANT CONNECT, RESOURCE, DBA TO champ_theatre;
GRANT CREATE SESSION TO champ_theatre;
GRANT CREATE TABLE TO champ_theatre;
GRANT CREATE VIEW TO champ_theatre;
GRANT CREATE ANY TRIGGER TO champ_theatre;
GRANT CREATE ANY PROCEDURE TO champ_theatre;
GRANT CREATE SEQUENCE TO champ_theatre;
GRANT CREATE SYNONYM TO champ_theatre;
GRANT UNLIMITED TABLESPACE TO champ_theatre;

```
