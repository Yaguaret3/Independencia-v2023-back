package com.megajuegos.independencia.config.socket;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionTrackingService {

    private final Map<String, Set<String>> userSessions = new ConcurrentHashMap<>();

    public synchronized boolean registerSession(String userId, String sessionId) {
        userSessions.putIfAbsent(userId, new HashSet<>());
        Set<String> sessions = userSessions.get(userId);

        if (sessions.size() >= 2) {
            return false; // Ya tiene 2 sesiones abiertas
        }

        sessions.add(sessionId);
        return true;
    }

    public synchronized void unregisterSession(String sessionId) {
        userSessions.forEach((userId, sessions) -> {
            sessions.remove(sessionId);
        });
        userSessions.entrySet().removeIf(entry -> entry.getValue().isEmpty());
    }

    public synchronized int getSessionCount(String userId) {
        return userSessions.getOrDefault(userId, Collections.emptySet()).size();
    }
}
