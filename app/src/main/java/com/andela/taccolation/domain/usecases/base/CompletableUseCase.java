package com.andela.taccolation.domain.usecases.base;

import androidx.lifecycle.LiveData;

public abstract class CompletableUseCase<Output> {

    protected abstract LiveData<Output> generateObservable();

    public LiveData<Output> buildUseCase() {
        return generateObservable();
    }
}
