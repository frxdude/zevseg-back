package com.zevseg.web.serviceImpl;

import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.helper.Localization;
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
    Localization localization;

    @Autowired
    public RankServiceImpl(RankRepository repository, Localization localization) {
        this.repository = repository;
        this.localization = localization;
    }

    /**
     * @param id  Long
     * @param req {@link HttpServletRequest}
     * @return {@link Branch}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public Rank find(Long id, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[find][input][id=" + id + "]");
            Rank rank = repository.findById(id)
                    .orElseThrow(() -> new BusinessException(localization.getMessage("data.not.found"), "Rank not found"));
            Logger.info(getClass().getName(), "[find][output][]");
            return rank;
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[find][output][" + ex.getMessage() + "]");
            throw ex;
        }catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[find][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
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
