package com.surveymoney.model;

import com.surveymoney.enumulation.SurveyState;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sv_base")
@Data
public class SurveyBase extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private SurveyState state;

    //private List<SurveyQuestion> surveyQuestionList;

}
