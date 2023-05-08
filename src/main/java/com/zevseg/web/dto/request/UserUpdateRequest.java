package com.zevseg.web.dto.request;

import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.entity.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * UserUpdateRequest
 *
 * @author Sainjargal Ishdorj
 **/

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String email;

    @Pattern(regexp = "([A-Za-zА-Яа-яөүӨҮёЁ. -]+)", message = "{val.letters}")
    @NotNull(message = "{val.not.null}")
    private String firstname;

    @Pattern(regexp = "([A-Za-zА-Яа-яөүӨҮёЁ. -]+)", message = "{val.letters}")
    @NotNull(message = "{val.not.null}")
    private String lastname;

    private String image;

    private Long rank;

    private Long branch;

}
