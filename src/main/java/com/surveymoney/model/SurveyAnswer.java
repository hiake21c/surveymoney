package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="sv_answer")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonBackReference
    private SurveyQuestion surveyQuestion;
}
