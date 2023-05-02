package com.zevseg.web.service;

import com.zevseg.web.dto.request.BranchAddRequest;
import com.zevseg.web.entity.Branch;
import com.zevseg.web.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

/**
 * BranchService
 *
 * @author Sainjargal Ishdorj
 **/

public interface BranchService {

    Page<Branch> findAll(int page, int size, HttpServletRequest req);

    void add(BranchAddRequest addRequest, HttpServletRequest req) throws BusinessException;

}
