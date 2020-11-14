package com.andela.taccolation.test;

import com.andela.taccolation.presentation.model.Student;
import com.andela.taccolation.presentation.model.StudentStatistics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileTest {

    private static FirebaseAuth mFirebaseAuth;
    private static FirebaseStorage mFirebaseStorage;
    private Student student;

    @BeforeClass
    public static void setUp() throws Exception {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseStorage = FirebaseStorage.getInstance();
    }

    @AfterClass
    public static void tearDown() {
        mFirebaseAuth = null;
        mFirebaseStorage = null;
    }

    @Before
    public void init() {
        student = mock(Student.class);
        Map<String, StudentStatistics> studentsStatsPerCourse = new HashMap<>();
        studentsStatsPerCourse.put("MAT 111", new StudentStatistics());
        studentsStatsPerCourse.put("TCS 407", new StudentStatistics());
        when(student).thenReturn(new Student("Adebisi", "Yusuf", "Nale", Arrays.asList("MAT 111", "TCS 407"), "", "", 20, 2034, studentsStatsPerCourse));
    }

    @Test
    public void addStudent() {
        Assert.assertEquals("Adebisi", student.getFirstName());
    }

    @Test
    public void getStudentList() {
    }

    @Test
    public void saveProfileImage() {
    }

    @Test
    public void getCourseList() {
    }
}