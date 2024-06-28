package com.indusnet.ums.service;

import com.indusnet.ums.dto.ResultResponse;
import com.indusnet.ums.model.Result;
import com.indusnet.ums.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;



    public ResultResponse submitResult(Result result) {
        Result savedResult = resultRepository.save(result);
        return new ResultResponse(
                savedResult.getQuiz().getId(),
                savedResult.getQuestion().getId(),
                savedResult.getQuestionText(),
                savedResult.getSubmittedAnswer(),
                "Your answer submitted successfully"
        );
    }
}