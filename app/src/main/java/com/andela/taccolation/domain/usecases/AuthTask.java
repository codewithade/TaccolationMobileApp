package com.andela.taccolation.domain.usecases;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.domain.repository.AuthRepo;
import com.andela.taccolation.domain.usecases.base.ObservableUseCase;
import com.andela.taccolation.presentation.model.Teacher;

import javax.inject.Inject;

public class AuthTask extends ObservableUseCase<AuthenticationState, Teacher> {

    private AuthRepo mAuthRepo;

    @Inject
    public AuthTask(AuthRepo authRepo) {
        mAuthRepo = authRepo;
    }

    @Override
    protected LiveData<AuthenticationState> generateObservable(Teacher input) {
        return mAuthRepo.signUpTeacher(input);
    }

    public LiveData<AuthenticationState> signInTeacher(String email, String password) {
        return mAuthRepo.signInTeacher(email, password);
    }

    public LiveData<AuthenticationState> getTeacherAuthState() {
        return mAuthRepo.getTeacherAuthState();
    }

    public LiveData<AuthenticationState> sendVerificationEmail() {
        return mAuthRepo.sendVerificationEmail();
    }

    public LiveData<TaskStatus> sendPasswordResetLink(String email) {
        return mAuthRepo.sendPasswordResetLink(email);
    }

    public LiveData<Teacher> getTeacherDetails() {
        return mAuthRepo.getTeacherDetails();
    }
}
