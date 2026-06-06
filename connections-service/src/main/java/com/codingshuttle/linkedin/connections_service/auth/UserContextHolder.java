package com.codingshuttle.linkedin.connections_service.auth;

public class UserContextHolder {
    private static final ThreadLocal<Long> currentUser = new ThreadLocal<>();

    public static Long getCurrentUserId() {
        return currentUser.get();
    }

    static void setCurrentUserId(Long userId) {
        currentUser.set(userId);
    }

    static void clearCurrentUserId() {
        currentUser.remove();
    }
}
