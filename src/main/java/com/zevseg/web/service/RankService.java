package com.zevseg.web.service;

import com.zevseg.web.entity.Rank;
import com.zevseg.web.exception.BusinessException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * RankService
 *
 * @author Sainjargal Ishdorj
 **/

public interface RankService {

    Rank find(Long id, HttpServletRequest req) throws BusinessException;

    List<Rank> findAll(HttpServletRequest req);

}
