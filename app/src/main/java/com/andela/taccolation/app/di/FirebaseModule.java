package com.andela.taccolation.app.di;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@InstallIn(ApplicationComponent.class)
@Module
public abstract class FirebaseModule {

    // @Provides tell Dagger how to create instances of the type that this function
    // returns (i.e. FirebaseAuth.getInstance()).
    // Function parameters are the dependencies of this type (No dependencies in this case).
    @Singleton
    @Provides
    public static FirebaseAuth provideFirebaseAuth() {
        // Whenever Dagger needs to provide an instance of type FirebaseAuth.getInstance(),
        // this code (the one inside the @Provides method) will be run.
        return FirebaseAuth.getInstance();
    }

    @Singleton
    @Provides
    public static FirebaseFirestore provideFirebaseFirestore() {
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    public static FirebaseStorage provideFirebaseStorage() {
        return FirebaseStorage.getInstance();
    }

}
