package com.andela.taccolation.app.di;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

// https://developer.android.com/guide/background/threading
@InstallIn(ApplicationComponent.class)
@Module
public abstract class ThreadModule {
    private static final int NUMBER_OF_THREADS = 4;

    @Singleton
    @Provides
    public static ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    }

    @Singleton
    @Binds
    public abstract Executor bindExecutor(ExecutorService executorService);
}
