package com.zevseg.web.dto.request;

import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserAddRequest
 *
 * @author Sainjargal Ishdorj
 **/

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserAddRequest {

    @NotNull(message = "{val.not.null}")
    private Rank rank;

    @NotBlank(message = "{val.not.null}")
    @Pattern(regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])", message = "{val.invalid}")
    private String email;

    @NotBlank(message = "{val.not.null}")
    @Size(min = 6, max = 30, message = "{val.length}")
    @Pattern.List({
            @Pattern(regexp = ".*[0-9].*", message = "Password must contain one digit."),
            @Pattern(regexp = ".*[a-z].*", message = "Password must contain one lowercase letter."),
            @Pattern(regexp = ".*[A-Z].*", message = "Password must contain one uppercase letter."),
            @Pattern(regexp = ".*\\S+$.*", message = "Password must contain no whitespace."),
            @Pattern(regexp = ".*\\W.*", message = "Password must contain one special character.")
    })
    private String password;

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
