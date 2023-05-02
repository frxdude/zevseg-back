package com.zevseg.web.serviceImpl;

import com.zevseg.web.dto.request.BranchAddRequest;
import com.zevseg.web.entity.Branch;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.helper.Localization;
import com.zevseg.web.repository.BranchRepository;
import com.zevseg.web.service.BranchService;
import com.zevseg.web.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * BranchServiceImpl
 *
 * @author Sainjargal Ishdorj
 **/

@Service
public class BranchServiceImpl implements BranchService {

    BranchRepository repository;
    Localization localization;

    @Autowired
    public BranchServiceImpl(BranchRepository repository, Localization localization) {
        this.repository = repository;
        this.localization = localization;
    }

    /**
     * @param page int
     * @param size int
     * @param req  {@link HttpServletRequest}
     * @return Page of {@link Branch}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public Page<Branch> findAll(int page, int size, HttpServletRequest req) {
        try {
            Logger.info(getClass().getName(), "[findAll][input][page=" + page + ", size=" + size + "]");
            Page<Branch> branchPage = repository.findAll(PageRequest.of(page, size));
            Logger.info(getClass().getName(), "[findAll][input][totalElements=" + branchPage.getTotalElements() + ", size=" + branchPage.getSize() + "]");
            return branchPage;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[findAll][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    @Override
    public void add(BranchAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[add][input][" + addRequest.toString() + "]");
            if(repository.findByName(addRequest.getName()).isPresent())
                throw new BusinessException(localization.getMessage("data.already"), "Branch already exists");
            Logger.info(getClass().getName(), "[add][output][Created]");
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[add][output][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[add][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }
}
