package com.andela.taccolation.app.di;

import com.andela.taccolation.data.remotedata.RemoteAssessmentDataSource;
import com.andela.taccolation.data.remotedata.RemoteAttendanceDataSource;
import com.andela.taccolation.data.remotedata.RemoteAuthDataSource;
import com.andela.taccolation.data.remotedata.RemoteLeaderboardDataSource;
import com.andela.taccolation.data.remotedata.RemoteProfileDataSource;
import com.andela.taccolation.data.remotedata.RemoteReportSheetDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherNotesDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherProfileDataSource;
import com.andela.taccolation.remote.source.RemoteAssessmentImpl;
import com.andela.taccolation.remote.source.RemoteAttendanceImpl;
import com.andela.taccolation.remote.source.RemoteAuthImpl;
import com.andela.taccolation.remote.source.RemoteLeaderboardImpl;
import com.andela.taccolation.remote.source.RemoteProfileImpl;
import com.andela.taccolation.remote.source.RemoteReportSheetImpl;
import com.andela.taccolation.remote.source.RemoteTeacherNotesImpl;
import com.andela.taccolation.remote.source.RemoteTeacherProfileImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public abstract class RemoteModule {

    @ActivityScoped
    @Binds
    public abstract RemoteAssessmentDataSource bindRemoteAssessmentDataSource(RemoteAssessmentImpl remoteAssessment);

    @ActivityScoped
    @Binds
    public abstract RemoteAttendanceDataSource bindRemoteAttendanceDataSource(RemoteAttendanceImpl remoteAttendance);

    @ActivityScoped
    @Binds
    public abstract RemoteAuthDataSource bindRemoteAuthDataSource(RemoteAuthImpl remoteAuth);

    @ActivityScoped
    @Binds
    public abstract RemoteLeaderboardDataSource bindRemoteLeaderboardDataSource(RemoteLeaderboardImpl remoteLeaderboard);

    @ActivityScoped
    @Binds
    public abstract RemoteReportSheetDataSource bindRemoteReportSheetDataSource(RemoteReportSheetImpl remoteReportSheet);

    @ActivityScoped
    @Binds
    public abstract RemoteProfileDataSource bindRemoteStudentProfileDataSource(RemoteProfileImpl remoteStudentProfile);

    @ActivityScoped
    @Binds
    public abstract RemoteTeacherProfileDataSource bindRemoteTeacherProfileDataSource(RemoteTeacherProfileImpl remoteTeacherProfile);

    @ActivityScoped
    @Binds
    public abstract RemoteTeacherNotesDataSource bindRemoteTeacherNotesDataSource(RemoteTeacherNotesImpl remoteTeacherNotes);
}
