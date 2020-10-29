package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.TeacherProfileRepo;

import javax.inject.Inject;

public class TeacherProfileTask {

    private TeacherProfileRepo mTeacherProfileRepo;

    @Inject
    public TeacherProfileTask(TeacherProfileRepo teacherProfileRepo) {
        mTeacherProfileRepo = teacherProfileRepo;
    }
}
