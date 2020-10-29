package com.andela.taccolation.app.di;

import com.andela.taccolation.data.AssessmentRepoImpl;
import com.andela.taccolation.data.AttendanceRepoImpl;
import com.andela.taccolation.data.AuthRepoImpl;
import com.andela.taccolation.data.LeaderboardRepoImpl;
import com.andela.taccolation.data.ProfileRepoImpl;
import com.andela.taccolation.data.ReportSheetRepoImpl;
import com.andela.taccolation.data.TeacherNotesRepoImpl;
import com.andela.taccolation.data.TeacherProfileRepoImpl;
import com.andela.taccolation.domain.repository.AssessmentRepo;
import com.andela.taccolation.domain.repository.AttendanceRepo;
import com.andela.taccolation.domain.repository.AuthRepo;
import com.andela.taccolation.domain.repository.LeaderboardRepo;
import com.andela.taccolation.domain.repository.ProfileRepo;
import com.andela.taccolation.domain.repository.ReportSheetRepo;
import com.andela.taccolation.domain.repository.TeacherNotesRepo;
import com.andela.taccolation.domain.repository.TeacherProfileRepo;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public abstract class DataModule {

    @ActivityScoped
    @Binds
    public abstract AssessmentRepo bindAssessmentRepo(AssessmentRepoImpl assessmentRepoImpl);

    @ActivityScoped
    @Binds
    public abstract AttendanceRepo bindAttendanceRepo(AttendanceRepoImpl attendanceRepoImpl);

    @ActivityScoped
    @Binds
    public abstract AuthRepo bindAuthRepo(AuthRepoImpl authRepoImpl);

    @ActivityScoped
    @Binds
    public abstract LeaderboardRepo bindLeaderboardRepo(LeaderboardRepoImpl leaderboardRepoImpl);

    @ActivityScoped
    @Binds
    public abstract ReportSheetRepo bindReportSheetRepo(ReportSheetRepoImpl reportSheetRepoImpl);

    @ActivityScoped
    @Binds
    public abstract ProfileRepo bindStudentProfileRepo(ProfileRepoImpl profileRepoImpl);

    @ActivityScoped
    @Binds
    public abstract TeacherNotesRepo bindTeacherNotesRepo(TeacherNotesRepoImpl teacherNotesRepoImpl);

    @ActivityScoped
    @Binds
    public abstract TeacherProfileRepo bindsTeacherProfileRepo(TeacherProfileRepoImpl teacherProfileRepoImpl);
}
