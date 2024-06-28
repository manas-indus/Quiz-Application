package com.indusnet.ums.dto;

import lombok.Data;

@Data
public class ResultResponse {
    private Long quizId;
    private Long questionId;
    private String questionText;
    private String submittedAnswer;
    private String message;

    public ResultResponse(Long quizId, Long questionId, String questionText, String submittedAnswer, String message) {
        this.quizId = quizId;
        this.questionId = questionId;
        this.questionText = questionText;
        this.submittedAnswer = submittedAnswer;
        this.message = message;
    }
}