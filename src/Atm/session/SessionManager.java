package Atm.session;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private static final Map<String, Integer> activeSessions = new HashMap<>();

    public static String createSession(int userId) {
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, userId);
        return sessionId;
    }

    public static Integer getUserId(String sessionId) {
        return activeSessions.get(sessionId);
    }

    public static void removeSession(String sessionId) {
        activeSessions.remove(sessionId);
    }
}