package com.zevseg.web.serviceImpl;

import com.zevseg.web.dto.request.auth.LoginRequest;
import com.zevseg.web.dto.request.auth.LoginResponse;
import com.zevseg.web.entity.User;
import com.zevseg.web.exception.BusinessException;
import com.zevseg.web.helper.Localization;
import com.zevseg.web.jwt.JwtTokenProvider;
import com.zevseg.web.repository.UserRepository;
import com.zevseg.web.service.AuthService;
import com.zevseg.web.service.UserService;
import com.zevseg.web.util.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Sainjargal Ishdorj
 * @created 2022.05.31
 */

@Service
public class AuthServiceImpl implements AuthService {

    UserService userService;
    UserRepository userRepository;
    AuthenticationManager authenticationManager;
    JwtTokenProvider jwtTokenProvider;
    PasswordEncoder encoder;
    Localization localization;

    @Autowired
    public AuthServiceImpl(UserService userService, UserRepository userRepository, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, PasswordEncoder encoder, Localization localization) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.encoder = encoder;
        this.localization = localization;
    }

    /**
     * @param user     {@link User} entity
     * @param password String
     * @return {@link LoginResponse}
     * @throws BusinessException when username or password doesn't match
     * @author Sainjargal Ishdorj
     **/

    private LoginResponse authenticate(User user, String password) throws BusinessException, BadCredentialsException {
        try {
            Logger.info(getClass().getName(), "[authenticate][input][User(id=" + user.getId() + ", email=" + user.getEmail() + ")]");
            if (!encoder.matches(password, user.getPassword()))
                throw new BusinessException(localization.getMessage("auth.username.pass.not.match"));

            if (authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), password)).isAuthenticated()) {
                LoginResponse response = LoginResponse.builder()
                        .user(user)
                        .accessToken(jwtTokenProvider.createToken(user.getId(), user.getRoles().get(0)))
                        .build();
                Logger.info(getClass().getName(), "[authenticate][output][" + response.toString() + "]");
                return response;
            } else
                throw new BusinessException(localization.getMessage("auth.username.pass.not.match"));

        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[authenticate][output][" + ex.reason + "]");
            throw ex;
        } catch (BadCredentialsException ex) {
            Logger.fatal(getClass().getName(), "[authenticate][output][" + ex.getMessage() + "]", ex);
            throw new BusinessException(localization.getMessage("auth.inactive.user"));
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[authenticate][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

    /**
     * Nevtreh
     *
     * @param loginRequest {@link LoginRequest} DTO
     * @param req          {@link HttpServletRequest}
     * @return {@link ResponseEntity<Object>}
     * @throws BusinessException when username or password doesn't match
     */

    public ResponseEntity<Object> login(LoginRequest loginRequest, HttpServletRequest req) throws BusinessException {
        try {
            Logger.info(getClass().getName(), "[login][input][" + loginRequest.toString() + "]");
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new BusinessException(localization.getMessage("auth.username.pass.not.match")));
            LoginResponse response = authenticate(user, loginRequest.getPassword());
            Logger.info(getClass().getName(), "[login][output][" + response.getUser().toString() + "]");
            return ResponseEntity.ok(response);
        } catch (BusinessException ex) {
            Logger.warn(getClass().getName(), "[login][output][" + ex.reason + "]");
            throw ex;
        } catch (Exception ex) {
            Logger.fatal(getClass().getName(), "[login][output][" + ex.getMessage() + "]", ex);
            throw ex;
        }
    }

}
