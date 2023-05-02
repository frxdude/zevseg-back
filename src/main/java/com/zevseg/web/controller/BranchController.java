package com.zevseg.web.controller;

import com.zevseg.web.dto.ErrorResponse;
import com.zevseg.web.dto.request.BranchAddRequest;
import com.zevseg.web.dto.request.auth.LoginResponse;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.service.BranchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * BranchController
 *
 * @author Sainjargal Ishdorj
 **/

@Api(tags = "Branch")
@RestController
@RequestMapping("branches")
public class BranchController {

    BranchService service;

    @Autowired
    public BranchController(BranchService service) {
        this.service = service;
    }

    @ApiOperation(value = "Төвүүдийн мэдээлэл татах. | ROLE_ADMIN, ROLE_USER")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> findAll(@RequestParam int page,
                                          @RequestParam int size,
                                          HttpServletRequest req) {
        return ResponseEntity.ok().body(service.findAll(page, size, req));
    }

    @ApiOperation(value = "Хэрэглэгч мэдээлэл татах. | ROLE_ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> add(@RequestBody BranchAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        service.add(addRequest, req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
