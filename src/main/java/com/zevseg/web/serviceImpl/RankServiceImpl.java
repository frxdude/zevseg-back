package com.zevseg.web.serviceImpl;

import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.repository.RankRepository;
import com.zevseg.web.service.RankService;
import com.zevseg.web.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * RankServiceImpl
 *
 * @author Sainjargal Ishdorj
 **/

@Service
public class RankServiceImpl implements RankService {

    RankRepository repository;

    @Autowired
    public RankServiceImpl(RankRepository repository) {
        this.repository = repository;
    }

    /**
     * @param req           {@link HttpServletRequest}
     * @return {@link List<Rank>}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public List<Rank> findAll(HttpServletRequest req) {
        try {
            Logger.info(getClass().getName(), "[findAll][input][]");
            List<Rank> rankList = repository.findAll();
            Logger.info(getClass().getName(), "[findAll][input][size=" + rankList.size() + "]");
            return rankList;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[findAll][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }
}
