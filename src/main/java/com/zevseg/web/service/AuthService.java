package com.zevseg.web.service;

import com.zevseg.web.dto.request.auth.LoginRequest;
import com.zevseg.web.dto.request.auth.PasswordRequest;
import com.zevseg.web.exception.BusinessException;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    ResponseEntity<Object> login(LoginRequest loginRequest, HttpServletRequest req) throws BusinessException;

}