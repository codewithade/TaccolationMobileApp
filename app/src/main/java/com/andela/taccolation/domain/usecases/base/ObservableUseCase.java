package com.andela.taccolation.domain.usecases.base;

import androidx.lifecycle.LiveData;

public abstract class ObservableUseCase<O, I> {

    protected abstract LiveData<O> generateObservable(I input);

    public LiveData<O> buildUseCase(I input) {
        return generateObservable(input);
    }

}
