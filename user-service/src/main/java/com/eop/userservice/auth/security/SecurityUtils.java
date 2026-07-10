package com.eop.userservice.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private static final String USER_ID = "user_id";
    private static final String USER_ROLE = "user_role";

    private static String getUserCred() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            return (String) authentication.getPrincipal();
        }
        return null;
    }

    public static String getUserSpeficicDetail(String type) {
        if (USER_ID.equalsIgnoreCase(type)) {
            return getUserCred() != null ? getUserCred().split("|")[0] : null;
        } else if (USER_ROLE.equalsIgnoreCase(type)) {
            return getUserCred() != null ? getUserCred().split("|")[1] : null;
        }
        return null;
    }
}
