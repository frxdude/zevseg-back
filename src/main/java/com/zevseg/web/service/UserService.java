package com.zevseg.web.service;

import com.zevseg.web.dto.request.ChangePasswordRequest;
import com.zevseg.web.dto.request.SoldierAddRequest;
import com.zevseg.web.dto.request.UserAddRequest;
import com.zevseg.web.dto.request.UserUpdateRequest;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.entity.User;
import com.zevseg.web.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    User findById(String id) throws BusinessException;

    Page<User> findAll(String searchPattern, Long rankId, Long branchId, int page, int size, HttpServletRequest req) ;

    ResponseEntity<Object> me(HttpServletRequest req) throws BusinessException;

    ResponseEntity<Object> addUser(UserAddRequest addRequest, HttpServletRequest req) throws BusinessException;

    ResponseEntity<Object> addSoldier(SoldierAddRequest addRequest, HttpServletRequest req) throws BusinessException;

    ResponseEntity<Object> update(String id, UserUpdateRequest updateRequest, HttpServletRequest req) throws BusinessException;

    ResponseEntity<Object> delete(String userId, HttpServletRequest req) throws BusinessException;

    ResponseEntity<Object> changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest req) throws BusinessException;

}
