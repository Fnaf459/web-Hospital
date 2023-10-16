package ru.ulstu.is.sbapp.user.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum UserRole implements GrantedAuthority {
    ADMIN,
    USER;

    private static final String PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return PREFIX + this.name();
    }

    public static final class AsString {
        public static final String ADMIN = PREFIX + "ADMIN";
        public static final String USER = PREFIX + "USER";
    }
}