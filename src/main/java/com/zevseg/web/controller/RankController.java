package com.zevseg.web.controller;

import com.zevseg.web.dto.ErrorResponse;
import com.zevseg.web.dto.request.auth.LoginResponse;
import com.zevseg.web.service.RankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * RankController
 *
 * @author Sainjargal Ishdorj
 **/


@Api(tags = "Rank")
@RestController
@RequestMapping("ranks")
public class RankController {

    RankService service;

    @Autowired
    public RankController(RankService service) {
        this.service = service;
    }

    @ApiOperation(value = "Цолны мэдээлэл татах. | ROLE_ADMIN, ROLE_USER")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> findAll(HttpServletRequest req) {
        return ResponseEntity.ok().body(service.findAll(req));
    }

}
