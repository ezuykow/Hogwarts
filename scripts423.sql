SELECT student.name AS student, student.age, faculty.name AS faculty
FROM student LEFT JOIN faculty ON student.faculty_id = faculty.id;

SELECT student.name, student.age
FROM student JOIN avatar a on student.id = a.student_id;

