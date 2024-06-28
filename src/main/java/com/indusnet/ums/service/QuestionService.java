package com.indusnet.ums.service;

import com.indusnet.ums.exception.ResourceNotFoundException;
import com.indusnet.ums.model.Question;
import com.indusnet.ums.model.Quiz;
import com.indusnet.ums.repository.QuestionRepository;
import com.indusnet.ums.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Question createQuestion(Question question) {
        Quiz quiz = quizRepository.findById(question.getQuiz().getId()).orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Question not found"));
    }
}