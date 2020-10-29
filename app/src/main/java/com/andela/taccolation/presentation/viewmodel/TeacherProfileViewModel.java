package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.TeacherProfileTask;

public class TeacherProfileViewModel extends ViewModel {

    private TeacherProfileTask mTeacherProfileTask;

    @ViewModelInject
    public TeacherProfileViewModel(TeacherProfileTask teacherProfileTask) {
        mTeacherProfileTask = teacherProfileTask;
    }
}
