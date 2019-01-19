package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.surveymoney.enumulation.SurveyState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sv_base")
@Getter
@Setter
@ToString(exclude = "surveyQuestionList")
public class SurveyBase extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SurveyState state;

    //CascadeType.ALL : 영속성을 같이 관리하는 것
    //FetchType.LAZY : 연결된 도메인을 모두 조회는 안한다. 실행한것만 조회.
    //FetchType.EAGER : 연결된 도메인을 모두 조회 한다. 무한루프가 발생 가능 함.
    @OneToMany(mappedBy = "surveyBase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SurveyQuestion> surveyQuestionList;

    @Transient
    private List<SurveyQuestion> Questions;

    @Transient
    private List<SurveyAnswer> answers;

}
