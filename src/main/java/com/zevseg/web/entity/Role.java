package com.zevseg.web.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_ADMIN, ROLE_USER, ROLE_SOLDIER;

    public String getAuthority() {
        return name();
    }

}
