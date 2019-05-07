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
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="answer_content", nullable = false)
    private String answerContent;

    @Column(name="answer_count", columnDefinition="int default 0")
//    @ColumnDefault("0")
    private int answerCount;

    @Column(name="answer_check")
    private String answerCheck;

    @Enumerated(EnumType.STRING)
    @Column(name="shape_type")
    private AnswerShapeType shapeType;

    @Column(name="scale")
    private  int scale;

    @Column(name="content_answer")
    private String contentAnswer;

    @Column(name="use_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNoType useYn;

    @Column(name="display_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNoType displayYn;

    @ManyToOne
    @JsonBackReference
    private SurveyQuestion surveyQuestion;
}
