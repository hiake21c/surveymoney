package com.surveymoney.repository;

import com.surveymoney.model.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository  extends JpaRepository<SurveyAnswer, Long> {
}
