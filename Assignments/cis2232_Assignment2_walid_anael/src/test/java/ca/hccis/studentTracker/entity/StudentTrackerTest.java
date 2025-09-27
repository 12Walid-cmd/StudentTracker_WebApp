package ca.hccis.studentTracker.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class StudentTrackerTest {

    StudentTracker studentTracker;

    @BeforeEach
    void setUp() {
        System.out.println("Inside setUp method");
         studentTracker = new StudentTracker();
    }


    @Test
    void calculateWeeklyStudyProgress_OneMinuteStudy() {
        studentTracker.setStudyDuration(1);       // 1 minute/day = 7 min/week
        studentTracker.setWeeklyStudyGoal(420);   // 420 minutes/week goal
        double actual = studentTracker.calculateWeeklyStudyProgress();
        Assertions.assertEquals(0, actual);       // (7/420)*100 = 0 (integer division)
    }


    @Test
    void calculateWeeklyStudyProgress_TwentyMinutesStudy() {
        studentTracker.setStudyDuration(20);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        Assertions.assertEquals(33.33, actual, 0.01); // pass with tolerance
    }


    // 3. Exactly at the goal (60 min/day = 420 min/week)
    @Test
    void testAtGoal() {
        studentTracker.setStudyDuration(60);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertEquals(100, actual); // (420/420)*100 = 100
    }


    // 4. Above the goal (120 min/day)
    @Test
    void testAboveGoal() {
        studentTracker.setStudyDuration(120);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertEquals(200, actual); // (840/420)*100 = 200
    }
    // 5. Zero study time
    @Test
    void testZeroStudyTime() {
        studentTracker.setStudyDuration(0);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertEquals(0, actual);
    }

    // 6. Zero weekly goal (division by zero -> exception)
    @Test
    void testZeroWeeklyGoal() {
        studentTracker.setStudyDuration(60);
        studentTracker.setWeeklyStudyGoal(0);
        assertThrows(ArithmeticException.class, () -> studentTracker.calculateWeeklyStudyProgress());
    }

    // 7. Check progress is positive when studying
    @Test
    void testTwentyMinutesStudy() {
        studentTracker.setStudyDuration(20);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertEquals(33.0, actual, 0.01); // allows small rounding differences
    }

    // 8. Check progress is less than 100 when studying below the goal
    @Test
    void testProgressLessThanGoal() {
        studentTracker.setStudyDuration(10);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertTrue(actual < 100);
    }

    // 9. Check that no study means not positive progress
    @Test
    void testNoStudyNotPositive() {
        studentTracker.setStudyDuration(0);
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertFalse(actual > 0); // 0% progress should not be positive
    }

    // 10. Large study time should give large progress
    @Test
    void testVeryLargeStudy() {
        studentTracker.setStudyDuration(600); // 600 min/day = 4200/week
        studentTracker.setWeeklyStudyGoal(420);
        double actual = studentTracker.calculateWeeklyStudyProgress();
        assertTrue(actual >= 1000); // should be very high progress
    }

}