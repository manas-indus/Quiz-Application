package com.indusnet.ums.repository;

import com.indusnet.ums.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
