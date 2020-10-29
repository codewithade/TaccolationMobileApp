package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.AttendanceRepo;

import javax.inject.Inject;

public class AttendanceTask {
    private AttendanceRepo mAttendanceRepo;

    @Inject
    public AttendanceTask(AttendanceRepo attendanceRepo) {
        mAttendanceRepo = attendanceRepo;
    }
}
