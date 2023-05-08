package com.zevseg.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"modifiedDate", "password", "isActive", "hibernateLazyInitializer"})
@Entity
@Table(name = "USERS")
public class User extends Audit {

    public User(String password, String email, String firstname, String lastname, Rank rank) {
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.rank = rank;
    }

    @Id
    @Column(nullable = false, name = "ID", length = 45, unique = true)
    private String id;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "EMAIL")
    private String email;

//    @Pattern(regexp = "([A-Za-zА-Яа-яөүӨҮёЁ. -]+)", message = "{val.letters}")
    @NotNull(message = "{val.not.null}")
    @Column(name = "FIRSTNAME", length = 50)
    private String firstname;

    @Column(name = "IMAGE", length = 100)
    private String image;

//    @Pattern(regexp = "([A-Za-zА-Яа-яөүӨҮёЁ. -]+)", message = "{val.letters}")
//    @NotNull(message = "val.not.null")
    @Column(name = "LASTNAME", length = 50)
    private String lastname;

    @OneToOne
    @JoinColumn(name = "RANK_ID")
    private Rank rank;

    @ElementCollection(fetch = FetchType.EAGER)
    @NotNull(message = "{val.not.null}")
    private List<Role> roles;

    @OneToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @PrePersist
    private void prePersist() {
        if (getId() == null)
            setId(UUID.randomUUID().toString());

        String tempFirstname = getFirstname();
        String tempLastname = getLastname();

        if(tempFirstname.length() > 0)
            setFirstname(tempFirstname.substring(0, 1).toUpperCase() + tempFirstname.substring(1));

        if(tempLastname.length() > 0)
            setLastname(tempLastname.substring(0, 1).toUpperCase() + tempLastname.substring(1));

    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
