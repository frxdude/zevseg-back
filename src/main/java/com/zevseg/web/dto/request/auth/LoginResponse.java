package com.zevseg.web.dto.request.auth;

import com.zevseg.web.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;

@Builder
@ToString
@Getter
@Setter
public class LoginResponse {

    private User user;

    private String accessToken;

}
