package com.andela.taccolation.domain.usecases;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.domain.repository.TeacherNotesRepo;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;

import javax.inject.Inject;

public class TeacherNotesTask {

    private TeacherNotesRepo mTeacherNotesRepo;

    @Inject
    public TeacherNotesTask(TeacherNotesRepo teacherNotesRepo) {
        mTeacherNotesRepo = teacherNotesRepo;
    }

    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        return mTeacherNotesRepo.uploadTeacherFiles(teacher, pathList, fileNames, courseCode);
    }
}
