package com.andela.taccolation.domain.usecases;

import com.andela.taccolation.domain.repository.AssessmentRepo;

import javax.inject.Inject;

public class AssessmentTask {

    private AssessmentRepo mAssessmentRepo;

    @Inject
    public AssessmentTask(AssessmentRepo assessmentRepo) {
        mAssessmentRepo = assessmentRepo;
    }
}
