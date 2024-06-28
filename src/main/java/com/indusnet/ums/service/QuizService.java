package com.indusnet.ums.service;

import com.indusnet.ums.exception.ResourceNotFoundException;
import com.indusnet.ums.model.Quiz;
import com.indusnet.ums.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
    }
}