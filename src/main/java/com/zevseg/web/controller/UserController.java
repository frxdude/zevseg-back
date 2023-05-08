package com.zevseg.web.controller;

import com.zevseg.web.dto.ErrorResponse;
import com.zevseg.web.dto.request.SoldierAddRequest;
import com.zevseg.web.dto.request.UserAddRequest;
import com.zevseg.web.dto.request.UserUpdateRequest;
import com.zevseg.web.dto.request.auth.LoginResponse;
import com.zevseg.web.dto.request.ChangePasswordRequest;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.entity.Role;
import com.zevseg.web.entity.User;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author Sainjargal Ishdorj
 */

@Api(tags = "User")
@RestController
@RequestMapping("users")
public class UserController {

    UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @ApiOperation(value = "Хэрэглэгч мэдээлэл татах. | ROLE_ADMIN, ROLE_USER")
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
                                          @RequestParam(required = false, defaultValue = "") String searchPattern,
                                          @RequestParam(required = false, defaultValue = "0") Long rankId,
                                          @RequestParam(required = false, defaultValue = "0") Long branchId,
                                          HttpServletRequest req) {
        return ResponseEntity.ok().body(service.findAll(searchPattern, rankId, branchId, page, size, req));
    }

    @ApiOperation(value = "Хэрэглэгч мэдээлэл татах. | ", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "me", method = RequestMethod.GET)
    public ResponseEntity<Object> me(HttpServletRequest req) throws BusinessException {
        return service.me(req);
    }

    @ApiOperation(value = "Хэрэглэгч нэмэх. | ROLE_ADMIN", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        return service.addUser(addRequest, req);
    }

    @ApiOperation(value = "Хэрэглэгч нэмэх. | ROLE_ADMIN", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "soldiers", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Object> addSoldier(@Valid @RequestBody SoldierAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        return service.addSoldier(addRequest, req);
    }

    @ApiOperation(value = "Хэрэглэгчийн мэдээлэл засах. | ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @Valid @RequestBody UserUpdateRequest updateRequest, HttpServletRequest req) throws BusinessException {
        return service.update(id, updateRequest, req);
    }

    @ApiOperation(value = "Хэрэглэгчийн мэдээлэл устгах. | ROLE_ADMIN")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable String id, HttpServletRequest req) throws BusinessException {
        return service.delete(id, req);
    }

    @ApiOperation(value = "Хэрэглэгчийн мэдээлэл устгах. | ROLE_ADMIN, ROLE_USER")
    @ApiResponses(value = {
            @ApiResponse(code = 200, response = LoginResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 400, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 401, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 403, response = ErrorResponse.class, message = "{} Object буцна"),
            @ApiResponse(code = 500, response = ErrorResponse.class, message = "{} Object буцна"),
    })
    @RequestMapping(value = "password", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, HttpServletRequest req) throws BusinessException {
        return service.changePassword(changePasswordRequest, req);
    }
}
