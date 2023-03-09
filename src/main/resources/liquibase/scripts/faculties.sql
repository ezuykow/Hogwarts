-- liquibase formatted sql

-- changeset ezuykow:1
ALTER TABLE faculty
    RENAME TO faculties;

-- changeset ezuykow:2
CREATE INDEX faculties_nc_idx
    ON faculties (name, color);
