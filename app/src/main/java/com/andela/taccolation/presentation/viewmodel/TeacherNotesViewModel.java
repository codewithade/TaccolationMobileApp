package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.domain.usecases.TeacherNotesTask;
import com.andela.taccolation.presentation.model.Teacher;

import java.util.List;

public class TeacherNotesViewModel extends ViewModel {

    private TeacherNotesTask mTeacherNotesTask;

    @ViewModelInject
    public TeacherNotesViewModel(TeacherNotesTask teacherNotesTask) {
        mTeacherNotesTask = teacherNotesTask;
    }

    public LiveData<TaskStatus> uploadTeacherFiles(Teacher teacher, List<String> pathList, List<String> fileNames, String courseCode) {
        return mTeacherNotesTask.uploadTeacherFiles(teacher, pathList, fileNames, courseCode);
    }
}
