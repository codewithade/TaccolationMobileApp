package com.andela.taccolation.local.source;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.data.localdata.LocalAuthDataSource;
import com.andela.taccolation.presentation.model.Teacher;

import javax.inject.Inject;

public class LocalAuthImpl implements LocalAuthDataSource {

    @Inject
    public LocalAuthImpl() {
    }

    @Override
    public LiveData<AuthenticationState> signUpTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public LiveData<AuthenticationState> signInTeacher(String email, String password) {
        return null;
    }

    @Override
    public LiveData<AuthenticationState> getTeacherAuthState() {
        return null;
    }

    @Override
    public LiveData<AuthenticationState> sendVerificationEmail() {
        return null;
    }

    @Override
    public LiveData<TaskStatus> sendPasswordResetLink(String email) {
        return null;
    }

    @Override
    public LiveData<Teacher> getTeacherDetails() {
        return null;
    }
}
