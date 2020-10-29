package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.TeacherNotesTask;

public class TeacherNotesViewModel extends ViewModel {

    private TeacherNotesTask mTeacherNotesTask;

    @ViewModelInject
    public TeacherNotesViewModel(TeacherNotesTask teacherNotesTask) {
        mTeacherNotesTask = teacherNotesTask;
    }
}
