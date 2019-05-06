package com.surveymoney.service;

import com.surveymoney.model.SurveyBase;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SurveySearchService {

    List<SurveyBase> baseSearch(Specification<SurveyBase> search);

}
