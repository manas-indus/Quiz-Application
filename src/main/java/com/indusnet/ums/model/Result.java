package com.indusnet.ums.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "results")

public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String submittedAnswer;
    private String questionText;
}
