package com.example.ordermicroservice.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {
    CUSTOMER("CUSTOMER"),
    CHEF("CHEF"),
    MANAGER("MANAGER");

    private final String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
