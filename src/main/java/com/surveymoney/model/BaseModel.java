package com.surveymoney.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {

    @Column(nullable = false)
    private LocalDateTime modifyDate;

    @Column(nullable = false)
    private Long modifyId;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Long createId;

}
