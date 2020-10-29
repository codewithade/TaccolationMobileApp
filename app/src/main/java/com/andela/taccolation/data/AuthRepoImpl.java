package com.andela.taccolation.data;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalAuthDataSource;
import com.andela.taccolation.data.remotedata.RemoteAuthDataSource;
import com.andela.taccolation.domain.repository.AuthRepo;
import com.andela.taccolation.presentation.model.Teacher;

import javax.inject.Inject;

public class AuthRepoImpl implements AuthRepo {

    private RemoteAuthDataSource mRemoteAuthDataSource;
    private LocalAuthDataSource mLocalAuthDataSource;

    @Inject
    public AuthRepoImpl(RemoteAuthDataSource remoteAuthDataSource, LocalAuthDataSource localAuthDataSource) {
        mRemoteAuthDataSource = remoteAuthDataSource;
        mLocalAuthDataSource = localAuthDataSource;
    }

    @Override
    public LiveData<AuthenticationState> signUpTeacher(Teacher teacher) {
        return mRemoteAuthDataSource.signUpTeacher(teacher);
    }

    @Override
    public LiveData<AuthenticationState> signInTeacher(String email, String password) {
        return mRemoteAuthDataSource.signInTeacher(email, password);
    }

    @Override
    public LiveData<AuthenticationState> getTeacherAuthState() {
        return mRemoteAuthDataSource.getTeacherAuthState();
    }

    @Override
    public LiveData<AuthenticationState> sendVerificationEmail() {
        return mRemoteAuthDataSource.sendVerificationEmail();
    }

    @Override
    public LiveData<TaskStatus> sendPasswordResetLink(String email) {
        return mRemoteAuthDataSource.sendPasswordResetLink(email);
    }

    @Override
    public LiveData<Teacher> getTeacherDetails() {
        return mRemoteAuthDataSource.getTeacherDetails();
    }
}
