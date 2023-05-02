package com.zevseg.web.service;

import com.zevseg.web.entity.Rank;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * RankService
 *
 * @author Sainjargal Ishdorj
 **/

public interface RankService {

    List<Rank> findAll(HttpServletRequest req);

}
