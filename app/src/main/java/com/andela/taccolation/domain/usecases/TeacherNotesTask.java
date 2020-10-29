package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.TeacherNotesRepo;

import javax.inject.Inject;

public class TeacherNotesTask {

    private TeacherNotesRepo mTeacherNotesRepo;

    @Inject
    public TeacherNotesTask(TeacherNotesRepo teacherNotesRepo) {
        mTeacherNotesRepo = teacherNotesRepo;
    }
}
