ALTER TABLE student
    ALTER COLUMN age
        SET NOT NULL;


ALTER TABLE student
    ALTER COLUMN age
        SET DEFAULT 20;

ALTER TABLE student
    ADD CONSTRAINT check_age
        check ( age > 15 );

ALTER TABLE student
    ALTER COLUMN name
        SET NOT NULL;

ALTER TABLE student
    ADD CONSTRAINT unique_name
        unique (name);

ALTER TABLE faculty
    ADD CONSTRAINT unique_name_color
        unique (name, color);
