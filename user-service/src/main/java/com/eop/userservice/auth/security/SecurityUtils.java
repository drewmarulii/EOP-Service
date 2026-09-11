package com.eop.userservice.auth.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private static final String USER_ID = "user_id";
    private static final String USER_ROLE = "user_role";

    private static String getUserCred() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof String) {
            String principal =(String) authentication.getPrincipal();
            return principal;
        }
        return null;
    }

    public static String getUserSpeficicDetail(String type) {
        String userCred = getUserCred();
        if (USER_ID.equalsIgnoreCase(type)) {
            String userId = userCred != null ? userCred.split("\\|")[0] : null;
            return userId;
        } else if (USER_ROLE.equalsIgnoreCase(type)) {
            String userRole = userCred != null ? userCred.split("\\|")[1] : null;
            return userRole;
        }
        return null;
    }
}
