package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalAuthDataSource;
import com.andela.taccolation.data.remotedata.RemoteAuthDataSource;
import com.andela.taccolation.domain.repository.AuthRepo;

import javax.inject.Inject;

public class AuthRepoImpl implements AuthRepo {

    private RemoteAuthDataSource mRemoteAuthDataSource;
    private LocalAuthDataSource mLocalAuthDataSource;

    @Inject
    public AuthRepoImpl(RemoteAuthDataSource remoteAuthDataSource, LocalAuthDataSource localAuthDataSource) {
        mRemoteAuthDataSource = remoteAuthDataSource;
        mLocalAuthDataSource = localAuthDataSource;
    }
}
