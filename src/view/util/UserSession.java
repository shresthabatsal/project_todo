package view.util;

public class UserSession {
    private static String userId;

    public static void setUserId(String id) {
        userId = id;
    }

    public static String getUserId() {
        return userId;
    }
}
