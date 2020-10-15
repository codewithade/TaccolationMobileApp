package com.andela.taccolation.data;

import com.andela.taccolation.data.localdata.LocalAssessmentDataSource;
import com.andela.taccolation.data.remotedata.RemoteAssessmentDataSource;
import com.andela.taccolation.domain.repository.AssessmentRepo;

import javax.inject.Inject;

public class AssessmentRepoImpl implements AssessmentRepo {

    private RemoteAssessmentDataSource mRemoteAssessmentDataSource;
    private LocalAssessmentDataSource mLocalAssessmentDataSource;

    @Inject
    public AssessmentRepoImpl(RemoteAssessmentDataSource remoteAssessmentDataSource, LocalAssessmentDataSource localAssessmentDataSource) {
        mRemoteAssessmentDataSource = remoteAssessmentDataSource;
        mLocalAssessmentDataSource = localAssessmentDataSource;
    }
}
