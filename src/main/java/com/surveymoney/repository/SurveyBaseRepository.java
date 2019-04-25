package com.surveymoney.repository;

import com.surveymoney.model.SurveyBase;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyBaseRepository extends JpaRepository<SurveyBase, Long> {
    SurveyBase findAll(Specification<SurveyBase> search);
}
