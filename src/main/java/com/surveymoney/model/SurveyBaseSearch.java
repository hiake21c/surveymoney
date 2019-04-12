package com.surveymoney.model;

import com.surveymoney.enumulation.YesNoType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SurveyBaseSearch {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDateTime endDate;

    String title;

    YesNoType useYn;

}
