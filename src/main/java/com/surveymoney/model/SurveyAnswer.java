package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name="sv_answer")
@Getter
@Setter
public class SurveyAnswer extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String answerTile;

    @Column(columnDefinition="int default 0")
//    @ColumnDefault("0")
    private int answerCount;

    private String answerCheck;

    @ManyToOne
    private SurveyQuestion surveyQuestion;
}
