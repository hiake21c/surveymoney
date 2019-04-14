package com.surveymoney.model;

import com.surveymoney.common.SearchSpecification;
import com.surveymoney.enumulation.SurveyState;
import com.surveymoney.enumulation.YesNoType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static org.springframework.data.jpa.domain.Specification.where;

@Getter
@Setter
@ToString
public class SurveyBaseSearch extends SearchSpecification<SurveyBase> {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime endDate;

    String title;

    YesNoType useYn;

    private SurveyState stateType;

    @Override
    public Specification<SurveyBase> toSpec() {
        Specification<SurveyBase> spec = where(stateTypeEquel());

        return spec;
    }

    public Specification<SurveyBase> stateTypeEquel(){

        Specification<SurveyBase> searchCode = where(null);

        if (stateType != null) {
            searchCode = searchCode.and(isEqual(SurveyBase_.stateType, stateType));
        }

        return searchCode;
    }
}
