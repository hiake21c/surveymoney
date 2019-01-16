package com.surveymoney.model;

import com.surveymoney.enumulation.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sv_question")
@Data
public class SurveyQuestion extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String questionTitle;

    @Column(nullable = false)
    private QuestionType questionType;

    //private SurveyBase surveyBase;

    //private List<SurveyAnswer> surveyAnswerList;

}
