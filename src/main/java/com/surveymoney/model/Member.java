package com.surveymoney.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sv_member")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name="mem_name", nullable = false)
    @Size(min = 2, max = 10)
    private String memName;

    @Column(name="mem_id", nullable = false)
    @Size(min = 3, max = 15)
    private String memId;


}
