DROP DATABASE IF EXISTS cis2232_student_tracker;
CREATE DATABASE cis2232_student_tracker;
USE cis2232_student_tracker;

CREATE TABLE StudentStudyLog (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 studentName VARCHAR(50) NOT NULL,
                                 subject VARCHAR(50) NOT NULL,
                                 studyDate DATE NOT NULL,
                                 studyDurationMinutes INT NOT NULL,
                                 studyMethod VARCHAR(30) NOT NULL,
                                 dailyStudyGoal INT NOT NULL,
                                 weeklyStudyGoal INT NOT NULL,
                                 notes VARCHAR(255),
                                 createdDateTime DATETIME DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO StudentStudyLog (studentName, subject, studyDate, studyDurationMinutes, studyMethod, dailyStudyGoal, weeklyStudyGoal, notes)
VALUES
    ('Walid Anael', 'Java Programming', '2025-10-20', 120, 'Practice', 90, 600, 'Worked on JDBC exercises'),
    ('Maria Smith', 'Database Systems', '2025-10-21', 75, 'Reading', 60, 420, 'Reviewed normalization rules'),
    ('John Brown', 'Web Development', '2025-10-22', 100, 'Videos', 80, 500, 'Watched Spring MVC tutorial'),
    ('Sophie Lee', 'Networking Fundamentals', '2025-10-21', 60, 'Group Work', 60, 400, 'Group lab on IP addressing'),
    ('Carlos Diaz', 'Cybersecurity Basics', '2025-10-23', 90, 'Practice', 75, 450, 'Hands-on with Kali Linux');
