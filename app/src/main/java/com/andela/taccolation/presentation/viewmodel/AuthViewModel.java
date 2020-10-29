package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.domain.usecases.AuthTask;
import com.andela.taccolation.presentation.model.Teacher;

public class AuthViewModel extends ViewModel {

    private final AuthTask mAuthTask;

    @ViewModelInject
    public AuthViewModel(AuthTask authTask) {
        mAuthTask = authTask;
    }

    public LiveData<AuthenticationState> signUpTeacher(Teacher teacher) {
        return mAuthTask.buildUseCase(teacher);
    }

    public LiveData<AuthenticationState> signInTeacher(String email, String password) {
        return mAuthTask.signInTeacher(email, password);
    }

    public LiveData<AuthenticationState> getTeacherAuthState() {
        return mAuthTask.getTeacherAuthState();
    }

    public LiveData<AuthenticationState> sendVerificationEmail() {
        return mAuthTask.sendVerificationEmail();
    }

    public LiveData<TaskStatus> sendPasswordResetLink(String email) {
        return mAuthTask.sendPasswordResetLink(email);
    }

    public LiveData<Teacher> getTeacherDetails() {
        return mAuthTask.getTeacherDetails();
    }

}
