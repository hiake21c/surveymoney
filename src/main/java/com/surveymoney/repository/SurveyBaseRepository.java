package com.surveymoney.repository;

import com.surveymoney.model.SurveyBase;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SurveyBaseRepository extends JpaRepository<SurveyBase, Long>, JpaSpecificationExecutor<SurveyBase> {
}
