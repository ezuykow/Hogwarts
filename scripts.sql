SELECT *
FROM student
WHERE age>23 AND age<28;

SELECT name
FROM student;

SELECT *
FROM student
WHERE name LIKE '%O%'
   or name LIKE '%o%';

SELECT *
FROM student
WHERE age < id;

SELECT *
FROM student
ORDER BY age;