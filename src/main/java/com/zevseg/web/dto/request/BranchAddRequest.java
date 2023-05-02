package com.zevseg.web.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * BranchAddRequest
 *
 * @author Sainjargal Ishdorj
 **/

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BranchAddRequest {

    @NotBlank(message = "{val.not.null}")
    private String name;

}
