package com.andela.taccolation.app.di;

import com.andela.taccolation.data.localdata.LocalAssessmentDataSource;
import com.andela.taccolation.data.localdata.LocalAttendanceDataSource;
import com.andela.taccolation.data.localdata.LocalAuthDataSource;
import com.andela.taccolation.data.localdata.LocalLeaderboardDataSource;
import com.andela.taccolation.data.localdata.LocalReportSheetDataSource;
import com.andela.taccolation.data.localdata.LocalStudentProfileDataSource;
import com.andela.taccolation.data.localdata.LocalTeacherNotesDataSource;
import com.andela.taccolation.data.localdata.LocalTeacherProfileDataSource;
import com.andela.taccolation.local.source.LocalAssessmentImpl;
import com.andela.taccolation.local.source.LocalAttendanceImpl;
import com.andela.taccolation.local.source.LocalAuthImpl;
import com.andela.taccolation.local.source.LocalLeaderboardImpl;
import com.andela.taccolation.local.source.LocalReportSheetImpl;
import com.andela.taccolation.local.source.LocalStudentProfileImpl;
import com.andela.taccolation.local.source.LocalTeacherNotesImpl;
import com.andela.taccolation.local.source.LocalTeacherProfileImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class LocalModule {

    @Binds
    public abstract LocalAssessmentDataSource bindLocalAssessmentDataSource(LocalAssessmentImpl localAssessment);

    @Binds
    public abstract LocalAttendanceDataSource bindLocalAttendanceDataSource(LocalAttendanceImpl localAttendance);

    @Binds
    public abstract LocalAuthDataSource bindLocalAuthDataSource(LocalAuthImpl localAuth);

    @Binds
    public abstract LocalLeaderboardDataSource bindLocalLeaderboardDataSource(LocalLeaderboardImpl localLeaderboard);

    @Binds
    public abstract LocalReportSheetDataSource bindLocalReportSheetDataSource(LocalReportSheetImpl localReportSheet);

    @Binds
    public abstract LocalStudentProfileDataSource bindLocalStudentProfileDataSource(LocalStudentProfileImpl localStudentProfile);

    @Binds
    public abstract LocalTeacherProfileDataSource bindLocalTeacherProfileDataSource(LocalTeacherProfileImpl localTeacherProfile);

    @Binds
    public abstract LocalTeacherNotesDataSource bindLocalTeacherNotesDataSource(LocalTeacherNotesImpl localTeacherNotes);
}
