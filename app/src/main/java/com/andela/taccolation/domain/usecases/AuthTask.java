package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.AuthRepo;

import javax.inject.Inject;

public class AuthTask {

    private AuthRepo mAuthRepo;

    @Inject
    public AuthTask(AuthRepo authRepo) {
        mAuthRepo = authRepo;
    }
}
