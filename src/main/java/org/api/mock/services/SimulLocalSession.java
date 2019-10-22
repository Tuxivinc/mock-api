package org.api.mock.services;

import org.api.mock.model.SessionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Simulation of local session
 */
@Service
public class SimulLocalSession {

    private static final Logger LOG = LoggerFactory.getLogger(SimulLocalSession.class);

    private static final Map<String, SessionResponse> SESSIONS = new HashMap<>();

    public SessionResponse getSession(String token) {
        LOG.debug("Get session for token {}", token);
        return Optional.ofNullable(SESSIONS.get(token))
                .orElseGet(() -> {
                    String valSession = getValSession();
                    SESSIONS.put(token, new SessionResponse(Boolean.TRUE, valSession));
                    return new SessionResponse(Boolean.FALSE, valSession);
                });
    }

    public static String getValSession() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS", Locale.FRANCE));
    }

}
