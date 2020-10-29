package com.andela.taccolation.presentation.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.ViewModel;

import com.andela.taccolation.domain.usecases.LeaderboardTask;

public class LeaderboardViewModel extends ViewModel {

    private LeaderboardTask mLeaderboardTask;

    @ViewModelInject
    public LeaderboardViewModel(LeaderboardTask leaderboardTask) {
        mLeaderboardTask = leaderboardTask;
    }
}
