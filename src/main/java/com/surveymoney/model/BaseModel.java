package com.surveymoney.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
public class BaseModel {

    @Column(nullable = false)
    //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifyDate;

    @Column(nullable = false)
    private Long modifyId;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Long createId;

}
