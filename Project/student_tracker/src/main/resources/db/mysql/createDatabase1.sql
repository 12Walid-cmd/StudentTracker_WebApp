DROP DATABASE IF EXISTS cis2232_squash_skills;
CREATE DATABASE cis2232_squash_skills;
use cis2232_squash_skills;

-- ------------------------------------------------------------------------------
-- Note the table below to hold data associated with your project.  Expect one
-- table with 7-9 fields.
-- ------------------------------------------------------------------------------

CREATE TABLE SkillsAssessmentSquashTechnical
(
    id                int(5),
    assessmentDate    varchar(10) NOT NULL COMMENT 'yyyy-MM-dd',
    createdDateTime   varchar(20) NOT NULL COMMENT 'yyyy-MM-dd hh:mm:ss',
    athleteName       varchar(50) NOT NULL COMMENT 'Athletes name',
    assessorName      varchar(50) NOT NULL COMMENT 'Athletes name',
    forehandDrives    int(5) COMMENT 'Number of forehand drives',
    backhandDrives    int(5) COMMENT 'Number of backhand drives',
    forehandVolleyMax int(5) COMMENT 'Max number of forehand volleys',
    forehandVolleySum int(5) COMMENT 'Sum of forehand volleys',
    backhandVolleyMax int(5) COMMENT 'Max number of backhand volleys',
    backhandVolleySum int(5) COMMENT 'Sum of backhand volleys',
    technicalScore    int(5) COMMENT 'Score calculated at submission'
) COMMENT 'This table holds technical skills assessment details';

ALTER TABLE SkillsAssessmentSquashTechnical
    ADD PRIMARY KEY (id);
ALTER TABLE SkillsAssessmentSquashTechnical
    MODIFY id int(4) NOT NULL AUTO_INCREMENT COMMENT 'This is the primary key',
    AUTO_INCREMENT = 1;

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
