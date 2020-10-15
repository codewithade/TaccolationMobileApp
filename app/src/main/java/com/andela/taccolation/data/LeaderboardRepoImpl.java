package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalLeaderboardDataSource;
import com.andela.taccolation.data.remotedata.RemoteLeaderboardDataSource;
import com.andela.taccolation.domain.repository.LeaderboardRepo;

import javax.inject.Inject;

public class LeaderboardRepoImpl implements LeaderboardRepo {

    private RemoteLeaderboardDataSource mRemoteLeaderboardDataSource;
    private LocalLeaderboardDataSource mLocalLeaderboardDataSource;

    @Inject
    public LeaderboardRepoImpl(RemoteLeaderboardDataSource remoteLeaderboardDataSource, LocalLeaderboardDataSource localLeaderboardDataSource) {
        mRemoteLeaderboardDataSource = remoteLeaderboardDataSource;
        mLocalLeaderboardDataSource = localLeaderboardDataSource;
    }
}
