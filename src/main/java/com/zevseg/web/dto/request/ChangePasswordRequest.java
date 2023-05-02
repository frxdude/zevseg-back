package com.zevseg.web.dto.request;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
/**
 * @author mmunkhsuld
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ChangePasswordRequest {

    @NotBlank(message = "val.not.null")
    @Size(min = 4, message = "val.length")
    private String oldPassword;

    @NotBlank(message = "val.not.null")
    @Size(min = 4, message = "val.length")
    private String newPassword;
}
