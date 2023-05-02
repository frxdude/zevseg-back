package com.zevseg.web.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Sainjargal Ishdorj
 * @created 2022.05.31
 * @goal
 */
@Getter
@Setter
public class MessageResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

}
