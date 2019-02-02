package com.surveymoney.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {

    @Column(nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifyDate;

    @Column(nullable = false, columnDefinition="999")
    private Long modifyId;

    @Column(nullable = false, updatable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createDate;

    @Column(nullable = false, columnDefinition="999")
    private Long createId;

}
