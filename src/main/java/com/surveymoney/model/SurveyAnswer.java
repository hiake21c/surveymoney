package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.surveymoney.enumulation.AnswerShapeType;
import com.surveymoney.enumulation.YesNoType;
import lombok.*;

import javax.persistence.*;

import static com.surveymoney.enumulation.YesNoType.*;

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
    private String answerContent;

    @Column(columnDefinition="int default 0")
//    @ColumnDefault("0")
    private int answerCount;

    private String answerCheck;

    @Enumerated(EnumType.STRING)
    private AnswerShapeType shapeType;

    private  int scale;

    private String contentAnswer;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNoType useYn;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNoType displayYn;

    @ManyToOne
    @JsonBackReference
    private SurveyQuestion surveyQuestion;
}
