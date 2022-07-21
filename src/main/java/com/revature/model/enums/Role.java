package com.revature.model.enums;

import org.springframework.security.core.GrantedAuthority;
public enum Role implements GrantedAuthority {
    CUSTOMER("CUSTOMER"), EMPLOYEE("EMPLOYEE");

    private final String value;

    Role(String authority) {
        this.value = authority;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}