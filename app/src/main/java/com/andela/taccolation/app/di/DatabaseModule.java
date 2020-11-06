package com.andela.taccolation.app.di;

import android.content.Context;

import androidx.room.Room;

import com.andela.taccolation.app.utils.Constants;
import com.andela.taccolation.local.database.AppDatabase;
import com.andela.taccolation.local.database.NotesDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ApplicationComponent.class)
// This annotation tells Hilt that the dependencies provided via this module shall stay alive as long as application is running.
@Module
// This annotation tells Hilt that this class contributes to dependency injection object graph.
public abstract class DatabaseModule {

    @Singleton
    @Provides // This annotation marks the method provideNotesDao as a provider of NotesDao.
    public static NotesDao provideNotesDao(AppDatabase appDatabase) {
        return appDatabase.notesDao();
    }

    @Singleton
    // @Singleton annotation tells Hilt that AppDatabase should be initialized only once. And the same instance should be provided every time it’s needed to be injected.
    @Provides
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) { // @ApplicationContext allows hilt to provide application context without having to explicitly specify how to obtain it.
        return Room.databaseBuilder(context, AppDatabase.class, Constants.DATABASE_NAME.getConstant()).build();
    }
}
