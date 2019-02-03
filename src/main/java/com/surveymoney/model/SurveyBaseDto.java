package com.surveymoney.model;

import com.surveymoney.enumulation.SurveyState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class SurveyBaseDto {

    @NotNull(message = "title is null")
    private String title;

    @NotNull
    private SurveyState state;

    private List<SurveyQuestionDto> Questions;




}
