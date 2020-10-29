package com.andela.taccolation.app.di;

import com.andela.taccolation.data.localdata.LocalAssessmentDataSource;
import com.andela.taccolation.data.localdata.LocalAttendanceDataSource;
import com.andela.taccolation.data.localdata.LocalAuthDataSource;
import com.andela.taccolation.data.localdata.LocalLeaderboardDataSource;
import com.andela.taccolation.data.localdata.LocalProfileDataSource;
import com.andela.taccolation.data.localdata.LocalReportSheetDataSource;
import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.data.localdata.LocalTeacherProfileDataSource;
import com.andela.taccolation.local.source.LocalAssessmentImpl;
import com.andela.taccolation.local.source.LocalAttendanceImpl;
import com.andela.taccolation.local.source.LocalAuthImpl;
import com.andela.taccolation.local.source.LocalLeaderboardImpl;
import com.andela.taccolation.local.source.LocalProfileImpl;
import com.andela.taccolation.local.source.LocalReportSheetImpl;
import com.andela.taccolation.local.source.LocalTeacherNotesImpl;
import com.andela.taccolation.local.source.LocalTeacherProfileImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public abstract class LocalModule {

    @ActivityScoped
    @Binds
    public abstract LocalAssessmentDataSource bindLocalAssessmentDataSource(LocalAssessmentImpl localAssessment);

    @ActivityScoped
    @Binds
    public abstract LocalAttendanceDataSource bindLocalAttendanceDataSource(LocalAttendanceImpl localAttendance);

    @ActivityScoped
    @Binds
    public abstract LocalAuthDataSource bindLocalAuthDataSource(LocalAuthImpl localAuth);

    @ActivityScoped
    @Binds
    public abstract LocalLeaderboardDataSource bindLocalLeaderboardDataSource(LocalLeaderboardImpl localLeaderboard);

    @ActivityScoped
    @Binds
    public abstract LocalReportSheetDataSource bindLocalReportSheetDataSource(LocalReportSheetImpl localReportSheet);

    @ActivityScoped
    @Binds
    public abstract LocalProfileDataSource bindLocalStudentProfileDataSource(LocalProfileImpl localStudentProfile);

    @ActivityScoped
    @Binds
    public abstract LocalTeacherProfileDataSource bindLocalTeacherProfileDataSource(LocalTeacherProfileImpl localTeacherProfile);

    @ActivityScoped
    @Binds
    public abstract LocalTeacherNotesDataSource bindLocalTeacherNotesDataSource(LocalTeacherNotesImpl localTeacherNotes);
}
