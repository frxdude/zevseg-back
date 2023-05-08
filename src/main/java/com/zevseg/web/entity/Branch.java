package com.zevseg.web.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;

/**
 * Branch
 *
 * @author Sainjargal Ishdorj
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"createdDate", "modifiedDate"})
@Entity
public class Branch {

    @Id
    @SequenceGenerator(name = "branchSeq", sequenceName = "BRANCH_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "branchSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

}
