package com.zevseg.web.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

/**
 * Rank
 *
 * @author Sainjargal Ishdorj
 **/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "RANKS")
public class Rank {

    @Id
    @SequenceGenerator(name = "rankSeq", sequenceName = "RANK_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "rankSeq")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    @Length(max = 50, min = 2, message = "{val.length}")
    private String name;

}
