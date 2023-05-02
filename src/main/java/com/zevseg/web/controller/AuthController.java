package com.zevseg.web.controller;

import com.zevseg.web.dto.ErrorResponse;
import com.zevseg.web.dto.request.auth.LoginRequest;
import com.zevseg.web.dto.request.auth.LoginResponse;
import com.zevseg.web.dto.request.auth.PasswordRequest;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "Auth")
@RestController
@RequestMapping("auth")
public class AuthController {

    AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @ApiOperation(value = "Нэвтрэх. | ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest req) throws BusinessException {
        return service.login(loginRequest, req);
    }
//
//    @ApiOperation(value = "Нууц үг мартсан. | ")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
//            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
//            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
//            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
//            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
//    })
//    @RequestMapping(value = "forgot", method = RequestMethod.GET)
//    public ResponseEntity<Object>  forgot(@Valid @RequestParam String email, HttpServletRequest req) throws BusinessException {
//        return service.forgot(email, req);
//    }

}
