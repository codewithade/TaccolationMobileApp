package com.andela.taccolation.presentation.viewmodel;

import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.AuthTask;

import javax.inject.Inject;

public class AuthViewModel extends ViewModel {

    private AuthTask mAuthTask;

    @Inject
    public AuthViewModel(AuthTask authTask) {
        mAuthTask = authTask;
    }
}
