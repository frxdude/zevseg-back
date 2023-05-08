package com.zevseg.web.service;

import com.zevseg.web.dto.request.BranchAddRequest;
import com.zevseg.web.entity.Branch;
import com.zevseg.web.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * BranchService
 *
 * @author Sainjargal Ishdorj
 **/

public interface BranchService {

    Branch find(Long id, HttpServletRequest req) throws BusinessException;

    Page<Branch> findAll(int page, int size, HttpServletRequest req);

    void add(BranchAddRequest addRequest, HttpServletRequest req) throws BusinessException;

}
