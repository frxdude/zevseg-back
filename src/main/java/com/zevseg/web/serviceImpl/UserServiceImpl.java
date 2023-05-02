package com.zevseg.web.serviceImpl;

import com.zevseg.web.dto.request.ChangePasswordRequest;
import com.zevseg.web.dto.request.SoldierAddRequest;
import com.zevseg.web.dto.request.UserAddRequest;
import com.zevseg.web.entity.Branch;
import com.zevseg.web.entity.Role;
import com.zevseg.web.entity.User;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.helper.Localization;
import com.zevseg.web.repository.BranchRepository;
import com.zevseg.web.repository.UserRepository;
import com.zevseg.web.service.UserService;
import com.zevseg.web.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    UserRepository userRepository;
    Localization localization;
    PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BranchRepository branchRepository, Localization localization, PasswordEncoder encoder) {
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
        this.localization = localization;
        this.encoder = encoder;
    }

    public User findById(String id) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[findById][input][id=" + id + "]");
            User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));
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
            User user = userRepository.findById(req.getRemoteUser()).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[me][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param page          int
     * @param size          int
     * @param req           {@link HttpServletRequest}
     * @return Page of {@link User}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public Page<User> findAll(int page, int size, HttpServletRequest req) {
        try {
            Logger.info(getClass().getName(), "[findAll][input][page="+ page + ", size=" + size + "]");
            Page<User> userPage = userRepository.findAll(PageRequest.of(page, size));
            Logger.info(getClass().getName(), "[findAll][input][totalElements="+ userPage.getTotalElements() + ", size=" + userPage.getSize() + "]");
            return userPage;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[findAll][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param addRequest {@link UserAddRequest}
     * @param req {@link HttpServletRequest}
     * @return {@link User}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public ResponseEntity<Object> addUser(UserAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[addUser][input][" + addRequest.toString() + "]");

            User user = User.builder()
                    .password(encoder.encode(addRequest.getPassword()))
                    .image(addRequest.getImage())
                    .lastname(addRequest.getLastname())
                    .firstname(addRequest.getFirstname())
                    .roles(Collections.singletonList(Role.ROLE_USER))
                    .rank(addRequest.getRank())
                    .email(addRequest.getEmail())
                    .build();

            if (addRequest.getBranches().size() != 1) {
                throw new BusinessException(localization.getMessage("branch.unable.add"));
            }

            checkBranches(addRequest.getBranches());
            user.setBranches(addRequest.getBranches());

            userRepository.save(user);
            Logger.info(getClass().getName(), "[addUser][output][Created]");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[addUser][output][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[addUser][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param addRequest {@link SoldierAddRequest}
     * @param req {@link HttpServletRequest}
     * @return {@link User}
     * @author Sainjargal Ishdorj
     **/

    @Override
    public ResponseEntity<Object> addSoldier(SoldierAddRequest addRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[addSoldier][input][" + addRequest.toString() + "]");

            User user = User.builder()
                    .image(addRequest.getImage())
                    .lastname(addRequest.getLastname())
                    .password("")
                    .firstname(addRequest.getFirstname())
                    .roles(Collections.singletonList(Role.ROLE_SOLDIER))
                    .rank(addRequest.getRank())
                    .build();

            if (addRequest.getBranches().size() != 1)
                throw new BusinessException(localization.getMessage("branch.unable.add"));

            checkBranches(addRequest.getBranches());
            user.setBranches(addRequest.getBranches());

            userRepository.save(user);
            Logger.info(getClass().getName(), "[addSoldier][output][Created]");
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[addSoldier][output][" + ex.getMessage() + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[addSoldier][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * @param userRequest {@link User}
     * @param req         {@link HttpServletRequest}
     * @return {@link ResponseEntity<Object>}
     * @throws BusinessException when user not found
     */

    @Override
    public ResponseEntity<Object> update(User userRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[updateUser][input][" + userRequest.toString() + "]");
            String userId = req.isUserInRole("ROLE_ADMIN") ? userRequest.getId() : req.getRemoteUser();

            User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));

            if (!user.getEmail().equals(userRequest.getEmail()) && userRepository.findByEmail(userRequest.getEmail()).isPresent())
                throw new BusinessException(localization.getMessage("user.already"));

            if (userRequest.getRoles().get(0).equals(Role.ROLE_SOLDIER) && userRequest.getBranches().size() > 1)
                throw new BusinessException(localization.getMessage("branch.unable.add"));

            user.setFirstname(userRequest.getFirstname());
            user.setLastname(userRequest.getLastname());
            user.setRank(userRequest.getRank());
            user.setImage(userRequest.getImage());
            user.setEmail(userRequest.getEmail());
            user.setRoles(userRequest.getRoles());
            user.setBranches(userRequest.getBranches());

            checkBranches(userRequest.getBranches());
            userRepository.save(user);
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
            User user = userRepository.findById(id).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));
            if (user.getRoles().get(0).equals(Role.ROLE_ADMIN)) {
                throw new BusinessException(localization.getMessage("error.unauthorized"));
            }
            userRepository.delete(user);
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
     * @param req {@link HttpServletRequest}
     * @return {@link ResponseEntity}
     * @throws BusinessException when user not found or password not match
     */
    @Override
    public ResponseEntity<Object> changePassword(ChangePasswordRequest changePasswordRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[changePassword][input][" + changePasswordRequest.toString() + "]");
            User user = userRepository.findById(req.getRemoteUser()).orElseThrow(() -> new BusinessException(localization.getMessage("user.not.found")));

            if (!encoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
                throw new BusinessException(localization.getMessage("auth.username.pass.not.match"));
            }

            user.setPassword(encoder.encode(changePasswordRequest.getNewPassword()));
            userRepository.save(user);
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
