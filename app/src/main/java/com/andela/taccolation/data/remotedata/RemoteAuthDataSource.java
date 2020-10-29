package com.andela.taccolation.data.remotedata;

import androidx.lifecycle.LiveData;

import com.andela.taccolation.app.utils.AuthenticationState;
import com.andela.taccolation.app.utils.TaskStatus;
import com.andela.taccolation.presentation.model.Teacher;

public interface RemoteAuthDataSource {

    LiveData<AuthenticationState> signUpTeacher(Teacher teacher);

    LiveData<AuthenticationState> signInTeacher(String email, String password);

    LiveData<AuthenticationState> getTeacherAuthState();

    LiveData<AuthenticationState> sendVerificationEmail();

    LiveData<TaskStatus> sendPasswordResetLink(String email);

    LiveData<Teacher> getTeacherDetails();
}
