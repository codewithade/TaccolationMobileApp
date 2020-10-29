package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.AttendanceTask;

public class AttendanceViewModel extends ViewModel {

    private AttendanceTask mAttendanceTask;

    @ViewModelInject
    public AttendanceViewModel(AttendanceTask attendanceTask) {
        mAttendanceTask = attendanceTask;
    }
}
