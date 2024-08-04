-- Create tables if they do not exist
CREATE TABLE IF NOT EXISTS course (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS student (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
    `group` VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `group` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS student_course (
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, course_id),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

CREATE TABLE IF NOT EXISTS teacher_course (
    teacher_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    PRIMARY KEY (teacher_id, course_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
);

-- Insert test data into course table
INSERT INTO course (id, name, type) VALUES (1, 'Course 1', 'MAIN');
INSERT INTO course (id, name, type) VALUES (2, 'Course 2', 'SECONDARY');

-- Insert test data into student table
INSERT INTO student (id, name, age, "group") VALUES (1, 'Student 1', 21, 'A');
INSERT INTO student (id, name, age, "group") VALUES (2, 'Student 2', 22, 'B');
INSERT INTO student (id, name, age, "group") VALUES (3, 'Student 3', 23, 'A');

-- Insert test data into teacher table
INSERT INTO teacher (id, name, age, "group") VALUES (1, 'Teacher 1', 41, 'A');
INSERT INTO teacher (id, name, age, "group") VALUES (2, 'Teacher 2', 42, 'B');

-- Insert test data into student_course table
INSERT INTO student_course (student_id, course_id) VALUES (1, 1);
INSERT INTO student_course (student_id, course_id) VALUES (2, 1);
INSERT INTO student_course (student_id, course_id) VALUES (3, 2);

-- Insert test data into teacher_course table
INSERT INTO teacher_course (teacher_id, course_id) VALUES (1, 1);
INSERT INTO teacher_course (teacher_id, course_id) VALUES (2, 1);