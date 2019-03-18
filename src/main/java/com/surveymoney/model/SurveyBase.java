package com.surveymoney.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.enumulation.YesNoType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sv_base")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SurveyBase extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SurveyState stateType;

    @Column(nullable = false, columnDefinition ="Y")
    @Enumerated(EnumType.STRING)
    private YesNoType useYn;

    @Column(nullable = false, columnDefinition ="Y")
    @Enumerated(EnumType.STRING)
    private YesNoType displayYn;

    //CascadeType.ALL : 영속성을 같이 관리하는 것
    //FetchType.LAZY : 연결된 도메인을 모두 조회는 안한다. 실행한것만 조회.
    //FetchType.EAGER : 연결된 도메인을 모두 조회 한다. 무한루프가 발생 가능 함.
    @OneToMany(mappedBy = "surveyBase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SurveyQuestion> surveyQuestionList;


}
