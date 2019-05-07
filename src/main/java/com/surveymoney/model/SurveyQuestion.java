package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.surveymoney.enumulation.QuestionType;
import com.surveymoney.enumulation.YesNoType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sv_question")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class SurveyQuestion extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="question_title", nullable = false)
    private String questionTitle;

    @Column(name="question_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Column(name="use_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNoType useYn;

    @Column(name="display_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNoType displayYn;

    @ManyToOne
    @JsonBackReference
    private SurveyBase surveyBase;



    @OneToMany(mappedBy = "surveyQuestion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SurveyAnswer> surveyAnswerList;


}
