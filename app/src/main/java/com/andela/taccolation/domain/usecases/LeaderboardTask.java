package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.LeaderboardRepo;

import javax.inject.Inject;

public class LeaderboardTask {

    private LeaderboardRepo mLeaderboardRepo;

    @Inject
    public LeaderboardTask(LeaderboardRepo leaderboardRepo) {
        mLeaderboardRepo = leaderboardRepo;
    }
}
