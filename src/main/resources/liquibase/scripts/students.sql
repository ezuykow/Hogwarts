-- liquibase formatted sql

-- changeset ezuykow:1
ALTER TABLE student
    RENAME TO students;

-- changeset ezuykow:2
CREATE INDEX students_name_idx
    ON students(name);