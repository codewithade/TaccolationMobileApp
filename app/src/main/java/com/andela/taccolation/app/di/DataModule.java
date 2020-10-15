package com.andela.taccolation.app.di;

import com.andela.taccolation.data.AssessmentRepoImpl;
import com.andela.taccolation.data.AttendanceRepoImpl;
import com.andela.taccolation.data.AuthRepoImpl;
import com.andela.taccolation.data.LeaderboardRepoImpl;
import com.andela.taccolation.data.ReportSheetRepoImpl;
import com.andela.taccolation.data.StudentProfileRepoImpl;
import com.andela.taccolation.data.TeacherNotesRepoImpl;
import com.andela.taccolation.data.TeacherProfileRepoImpl;
import com.andela.taccolation.domain.repository.AssessmentRepo;
import com.andela.taccolation.domain.repository.AttendanceRepo;
import com.andela.taccolation.domain.repository.AuthRepo;
import com.andela.taccolation.domain.repository.LeaderboardRepo;
import com.andela.taccolation.domain.repository.ReportSheetRepo;
import com.andela.taccolation.domain.repository.StudentProfileRepo;
import com.andela.taccolation.domain.repository.TeacherNotesRepo;
import com.andela.taccolation.domain.repository.TeacherProfileRepo;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class DataModule {

    @Binds
    public abstract AssessmentRepo bindAssessmentRepo(AssessmentRepoImpl assessmentRepoImpl);

    @Binds
    public abstract AttendanceRepo bindAttendanceRepo(AttendanceRepoImpl attendanceRepoImpl);

    @Binds
    public abstract AuthRepo bindAuthRepo(AuthRepoImpl authRepoImpl);

    @Binds
    public abstract LeaderboardRepo bindLeaderboardRepo(LeaderboardRepoImpl leaderboardRepoImpl);

    @Binds
    public abstract ReportSheetRepo bindReportSheetRepo(ReportSheetRepoImpl reportSheetRepoImpl);

    @Binds
    public abstract StudentProfileRepo bindStudentProfileRepo(StudentProfileRepoImpl studentProfileRepoImpl);

    @Binds
    public abstract TeacherNotesRepo bindTeacherNotesRepo(TeacherNotesRepoImpl teacherNotesRepoImpl);

    @Binds
    public abstract TeacherProfileRepo bindsTeacherProfileRepo(TeacherProfileRepoImpl teacherProfileRepoImpl);
}
