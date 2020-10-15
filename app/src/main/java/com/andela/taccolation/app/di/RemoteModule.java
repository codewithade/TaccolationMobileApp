package com.andela.taccolation.app.di;

import com.andela.taccolation.data.remotedata.RemoteAssessmentDataSource;
import com.andela.taccolation.data.remotedata.RemoteAttendanceDataSource;
import com.andela.taccolation.data.remotedata.RemoteAuthDataSource;
import com.andela.taccolation.data.remotedata.RemoteLeaderboardDataSource;
import com.andela.taccolation.data.remotedata.RemoteReportSheetDataSource;
import com.andela.taccolation.data.remotedata.RemoteStudentProfileDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherNotesDataSource;
import com.andela.taccolation.data.remotedata.RemoteTeacherProfileDataSource;
import com.andela.taccolation.remote.source.RemoteAssessmentImpl;
import com.andela.taccolation.remote.source.RemoteAttendanceImpl;
import com.andela.taccolation.remote.source.RemoteAuthImpl;
import com.andela.taccolation.remote.source.RemoteLeaderboardImpl;
import com.andela.taccolation.remote.source.RemoteReportSheetImpl;
import com.andela.taccolation.remote.source.RemoteStudentProfileImpl;
import com.andela.taccolation.remote.source.RemoteTeacherNotesImpl;
import com.andela.taccolation.remote.source.RemoteTeacherProfileImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class RemoteModule {

    @Binds
    public abstract RemoteAssessmentDataSource bindRemoteAssessmentDataSource(RemoteAssessmentImpl remoteAssessment);

    @Binds
    public abstract RemoteAttendanceDataSource bindRemoteAttendanceDataSource(RemoteAttendanceImpl remoteAttendance);

    @Binds
    public abstract RemoteAuthDataSource bindRemoteAuthDataSource(RemoteAuthImpl remoteAuth);

    @Binds
    public abstract RemoteLeaderboardDataSource bindRemoteLeaderboardDataSource(RemoteLeaderboardImpl remoteLeaderboard);

    @Binds
    public abstract RemoteReportSheetDataSource bindRemoteReportSheetDataSource(RemoteReportSheetImpl remoteReportSheet);

    @Binds
    public abstract RemoteStudentProfileDataSource bindRemoteStudentProfileDataSource(RemoteStudentProfileImpl remoteStudentProfile);

    @Binds
    public abstract RemoteTeacherProfileDataSource bindRemoteTeacherProfileDataSource(RemoteTeacherProfileImpl remoteTeacherProfile);

    @Binds
    public abstract RemoteTeacherNotesDataSource bindRemoteTeacherNotesDataSource(RemoteTeacherNotesImpl remoteTeacherNotes);

    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. FirebaseAuth.getInstance()).
    // Function parameters are the dependencies of this type (No dependencies in this case).
    /*@Provides
    public static FirebaseAuth provideFirebaseAuth() {
        // Whenever Dagger needs to provide an instance of type FirebaseAuth.getInstance(),
        // this code (the one inside the @Provides method) will be run.
        return FirebaseAuth.getInstance();
    }

    @Provides
    public static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }*/

}
