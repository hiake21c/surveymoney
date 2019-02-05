package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.surveymoney.enumulation.QuestionType;
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
    private Long id;

    @Column(nullable = false)
    private String questionTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @ManyToOne
    @JsonBackReference
    private SurveyBase surveyBase;

    @OneToMany(mappedBy = "surveyQuestion", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SurveyAnswer> surveyAnswerList;


}
