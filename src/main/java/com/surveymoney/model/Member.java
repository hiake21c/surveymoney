package com.surveymoney.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sv_member")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min = 2, max = 10)
    private String memName;

    @Column(nullable = false)
    @Size(min = 3, max = 15)
    private String memId;


}
