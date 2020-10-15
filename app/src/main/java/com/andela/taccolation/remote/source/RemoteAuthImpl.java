package com.andela.taccolation.remote.source;

import com.andela.taccolation.data.remotedata.RemoteAuthDataSource;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class RemoteAuthImpl implements RemoteAuthDataSource {

    private FirebaseAuth mFirebaseAuth;

    @Inject
    public RemoteAuthImpl(FirebaseAuth firebaseAuth) {
        mFirebaseAuth = firebaseAuth;
    }
}
