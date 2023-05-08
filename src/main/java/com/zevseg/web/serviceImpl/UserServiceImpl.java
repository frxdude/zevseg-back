package com.zevseg.web.serviceImpl;

import com.zevseg.web.dto.request.ChangePasswordRequest;
import com.zevseg.web.dto.request.SoldierAddRequest;
import com.zevseg.web.dto.request.UserAddRequest;
import com.zevseg.web.dto.request.UserUpdateRequest;
import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Rank;
import com.zevseg.web.entity.Role;
import com.zevseg.web.entity.User;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.helper.Localization;
import com.zevseg.web.helper.specification.SearchCriteria;
import com.zevseg.web.helper.specification.UserSpecification;
import com.zevseg.web.repository.BranchRepository;
import com.zevseg.web.repository.UserRepository;
import com.zevseg.web.service.BranchService;
import com.zevseg.web.service.RankService;
import com.zevseg.web.service.UserService;
import com.zevseg.web.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    BranchRepository branchRepository;
    RankService rankService;
    BranchService branchService;
    UserRepository repository;
    Localization localization;
    PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(BranchRepository branchRepository, RankService rankService, BranchService branchService, UserRepository repository, Localization localization, PasswordEncoder encoder) {
        this.branchRepository = branchRepository;
        this.rankService = rankService;
        this.branchService = branchService;
        this.repository = repository;
        this.localization = localization;
        this.encoder = encoder;
    }

    public User findById(String id) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[findById][input][id=" + id + "]");
            User user = repository.findById(id).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));
            Logger.info(getClass().getName(), "[findById][output][User(id=" + user.getId() + ")]");
            return user;
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[findById][" + ex.reason + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[findById][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    @Override
    public ResponseEntity<Object> me(HttpServletRequest req) throws BusinessException {
        try {
            User user = repository.findById(req.getRemoteUser()).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[me][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param searchPattern String
     * @param branchId      Long
     * @param rankId        Long
     * @param page          int
     * @param size          int
     * @param req           {@link HttpServletRequest}
     * @return Page of {@link User}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public Page<User> findAll(String searchPattern, Long rankId, Long branchId, int page, int size, HttpServletRequest req) {
        try {
            Logger.info(getClass().getName(), "[findAll][input][page=" + page + ", size=" + size + "]");

            UserSpecification rankSpec = rankId != 0
                    ? new UserSpecification(new SearchCriteria("rank", "=", rankId))
                    : new UserSpecification();

            UserSpecification branchSpec = branchId != 0
                    ? new UserSpecification(new SearchCriteria("branch", "=", branchId))
                    : new UserSpecification();

            UserSpecification firstnameSpec = new UserSpecification(new SearchCriteria("firstname", ".%", searchPattern));

            Page<User> userPage = repository.findAll(rankSpec.and(branchSpec.and(firstnameSpec))
                    , PageRequest.of(page, size, Sort.by("id").descending()));

            Logger.info(getClass().getName(), "[findAll][input][totalElements=" + userPage.getTotalElements() + ", size=" + userPage.getSize() + "]");
            return userPage;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[findAll][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param addRequest {@link UserAddRequest}
     * @param req        {@link HttpServletRequest}
     * @return {@link User}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public ResponseEntity<Object> addUser(UserAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[addUser][input][" + addRequest.toString() + "]");

            Rank rank = rankService.find(addRequest.getRank(), req);
            Branch branch = branchService.find(addRequest.getBranch(), req);

            User user = User.builder()
                    .password(encoder.encode(addRequest.getPassword()))
                    .image(addRequest.getImage())
                    .lastname(addRequest.getLastname())
                    .firstname(addRequest.getFirstname())
                    .roles(Collections.singletonList(Role.ROLE_USER))
                    .rank(rank)
                    .branch(branch)
                    .email(addRequest.getEmail())
                    .build();

            repository.save(user);
            Logger.info(getClass().getName(), "[addUser][output][Created]");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[addUser][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param addRequest {@link SoldierAddRequest}
     * @param req        {@link HttpServletRequest}
     * @return {@link User}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public ResponseEntity<Object> addSoldier(SoldierAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[addSoldier][input][" + addRequest.toString() + "]");

            Rank rank = rankService.find(addRequest.getRank(), req);
            Branch branch = branchService.find(addRequest.getBranch(), req);

            User user = User.builder()
                    .image(addRequest.getImage())
                    .lastname(addRequest.getLastname())
                    .password("")
                    .firstname(addRequest.getFirstname())
                    .roles(Collections.singletonList(Role.ROLE_SOLDIER))
                    .rank(rank)
                    .branch(branch)
                    .build();

            repository.save(user);
            Logger.info(getClass().getName(), "[addSoldier][output][Created]");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[addSoldier][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param id            String
     * @param updateRequest {@link UserUpdateRequest}
     * @param req           {@link HttpServletRequest}
     * @return {@link ResponseEntity<Object>}
     * @throws BusinessException when user not found
     */

    @Override
    public ResponseEntity<Object> update(String id, UserUpdateRequest updateRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[updateUser][input][" + updateRequest.toString() + "]");

            User user = repository.findById(id).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));

            if (!user.getRoles().get(0).equals(Role.ROLE_SOLDIER))

                if (!user.getEmail().equals(updateRequest.getEmail()) && repository.findByEmail(updateRequest.getEmail()).isPresent())
                    throw new BusinessException(localization.getMessage("user.already"));

//            if (updateRequest.getRoles().get(0).equals(Role.ROLE_SOLDIER) && updateRequest.getBranches().size() > 1)
//                throw new BusinessException(localization.getMessage("branch.unable.add"));

            if (!user.getRoles().get(0).equals(Role.ROLE_ADMIN)) {
                Rank rank = rankService.find(updateRequest.getRank(), req);
                Branch branch = branchService.find(updateRequest.getBranch(), req);
                user.setRank(rank);
                user.setBranch(branch);
            }
            user.setFirstname(updateRequest.getFirstname());
            user.setLastname(updateRequest.getLastname());
            user.setImage(updateRequest.getImage());
            user.setEmail(updateRequest.getEmail());

            repository.save(user);
            Logger.info(getClass().getName(), "[updateUser][output][Done]");
            return ResponseEntity.ok().build();
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[updateUser][output][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[updateUser][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * Бүртгэлтэй салбар эсэхийг шалгах
     *
     * @param branchList {@link List<Branch>}
     * @throws BusinessException when branch not found
     **/

    private void checkBranches(List<Branch> branchList) throws BusinessException {
        if (!branchList.isEmpty()) {
            List<Long> ids = new ArrayList<>();
            for (Branch branch : branchList) {
                ids.add(branch.getId());
            }
            if (branchRepository.countByIdIn(ids) != branchList.size()) {
                throw new BusinessException(localization.getMessage("branch.not.found"));
            }
        }
    }

    /**
     * @param id  String
     * @param req {@link HttpServletRequest}
     * @return {@link ResponseEntity<Object>}
     * @throws BusinessException when user not found
     **/

    @Override
    public ResponseEntity<Object> delete(String id, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[deleteUser][input][id=" + id + "]");
            User user = repository.findById(id).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));
            if (user.getRoles().get(0).equals(Role.ROLE_ADMIN)) {
                throw new BusinessException(localization.getMessage("error.unauthorized"));
            }
            repository.delete(user);
            Logger.info(getClass().getName(), "[deleteUser][output][Successfully deleted]");
            return ResponseEntity.noContent().build();
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[deleteUser][output][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[deleteUser][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param changePasswordRequest {@link ChangePasswordRequest}
     * @param req                   {@link HttpServletRequest}
     * @return {@link ResponseEntity}
     * @throws BusinessException when user not found or password not match
     */
    @Override
    public ResponseEntity<Object> changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[changePassword][input][" + changePasswordRequest.toString() + "]");
            User user = repository.findById(req.getRemoteUser()).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));

            if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
                throw new BusinessException(localization.getMessage("auth.username.pass.not.match"));
            }

            user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            repository.save(user);
            Logger.info(getClass().getName(), "[changePassword][output][Password successfully changed]");
            return ResponseEntity.ok().build();
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[changePassword][output][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[changePassword][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

}
