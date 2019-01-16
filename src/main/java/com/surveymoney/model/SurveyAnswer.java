package com.surveymoney.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="sv_answer")
@Data
public class SurveyAnswer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answerTile;

    private Long answerCount;

    private String answerCheck;

    //private SurveyQuestion surveyQuestion;
}
