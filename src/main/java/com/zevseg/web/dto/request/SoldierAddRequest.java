package com.zevseg.web.dto.request;

import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SoldierAddRequest
 *
 * @author Sainjargal Ishdorj
 **/

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SoldierAddRequest {

    @NotNull(message = "{val.not.null}")
    private Rank rank;

    @NotNull(message = "{val.not.null}")
    private String image;

    @Pattern(regexp = "([A-Za-zА-Яа-яөүӨҮёЁ. -]+)", message = "{val.letters}")
    @Length(min = 0, max = 50, message = "{val.length}")
    @NotNull(message = "{val.not.null}")
    private String firstname;

    @Pattern(regexp = "([A-Za-zА-Яа-яөүӨҮёЁ. -]+)", message = "{val.letters}")
    @Length(min = 0, max = 50, message = "{val.length}")
    @NotNull(message = "{val.not.null}")
    private String lastname;

    @NotEmpty(message = "{val.not.null}")
    private List<Branch> branches = new ArrayList<>();

}
