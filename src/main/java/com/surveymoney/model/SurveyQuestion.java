package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.surveymoney.enumulation.QuestionType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sv_question")
@Getter
@Setter
public class SurveyQuestion extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String questionTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne
    private SurveyBase surveyBase;

    @OneToMany(mappedBy = "surveyQuestion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SurveyAnswer> surveyAnswerList;


}
