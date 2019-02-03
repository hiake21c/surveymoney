package com.surveymoney.tests.model;

        import lombok.Getter;
        import lombok.Setter;
        import lombok.ToString;

        import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString(callSuper = true)
public class TestDto {

    //@NotNull
    private String name;

    //@NotNull
    private String description;
}
